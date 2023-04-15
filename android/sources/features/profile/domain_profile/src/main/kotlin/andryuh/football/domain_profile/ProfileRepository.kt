package andryuh.football.domain_profile

import android.content.Context
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import andryuh.football.core_kotlin.nullIfBlank
import andryuh.football.domain_profile.dto.Profile
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

@Singleton
class ProfileRepository
@Inject
constructor(
  private val context: Context,
  private val profileApi: ProfileApi,
  private val dataStore: DataStore<Preferences>,
) {

  private val phoneNumberPrefsKey = stringPreferencesKey("phoneNumber")

  private val profileCache = MutableStateFlow<Profile?>(null)

  fun observeProfile(): Flow<Profile> {
    CoroutineScope(Dispatchers.IO).launch { updateProfileCache() }

    return profileCache.filterNotNull()
  }

  suspend fun getProfile(): Profile {
    updateProfileCache()

    return profileCache.filterNotNull().last()
  }

  suspend fun updatePhoto(photo: Uri) {
    val phoneNumber = dataStore.data.first()[phoneNumberPrefsKey]!!

    val body =
      context.contentResolver.openInputStream(photo).use { inputStream ->
        val bytes = inputStream?.readBytes()!!

        RequestBody.create(MediaType.parse("image/*"), bytes)
      }

    val filePart =
      MultipartBody.Part.createFormData(
        /* name = */ "image",
        /* filename = */ "image$phoneNumber",
        /* body = */ body,
      )

    val updateResult =
      profileApi.updatePhoto(
        phoneNumber = phoneNumber,
        image = filePart,
      )

    if (!updateResult.isSuccessful) {
      throw HttpException(updateResult)
    }

    updateProfileCache()
  }
  suspend fun updateProfile(profile: Profile) {
    val phoneNumber = dataStore.data.first()[phoneNumberPrefsKey]!!

    val updateResult =
      profileApi.updateProfile(
        phoneNumber = phoneNumber,
        profileBody =
          profile.copy(
            footballExperience = profile.footballExperience.nullIfBlank(),
            tournamentsExperience = profile.tournamentsExperience.nullIfBlank(),
            contactInfo = profile.contactInfo.nullIfBlank(),
            about = profile.about.nullIfBlank(),
            imageUrl = profile.imageUrl.nullIfBlank(),
            fullName = profile.fullName.nullIfBlank(),
          )
      )

    if (!updateResult.isSuccessful) {
      throw HttpException(updateResult)
    }
    updateProfileCache()
  }

  private suspend fun updateProfileCache() {
    val phoneNumber = dataStore.data.first()[phoneNumberPrefsKey]!!

    profileCache.emit(profileApi.getProfile(phoneNumber))
  }
}
