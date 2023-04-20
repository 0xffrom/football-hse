package andryuh.football.domain_profile

import android.content.Context
import android.net.Uri
import andryuh.football.core_auth.PhoneStorage
import andryuh.football.core_kotlin.nullIfBlank
import andryuh.football.core_network.ext.throwExceptionIfError
import andryuh.football.core_network.ext.toRequestBody
import andryuh.football.domain_profile.dto.Profile
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

@Singleton
class ProfileRepository
@Inject
constructor(
  private val context: Context,
  private val profileApi: ProfileApi,
  private val phoneStorage: PhoneStorage
) {

  private val profileCache = MutableStateFlow<Profile?>(null)

  fun observeProfile(): Flow<Profile> {
    CoroutineScope(Dispatchers.IO).launch { updateProfileCache() }

    return profileCache.filterNotNull()
  }

  fun observeCaptain(): Flow<Boolean> = observeProfile().map { profile -> profile.isCaptain }

  suspend fun getProfile(): Profile {
    updateProfileCache()

    return profileCache.filterNotNull().first()
  }

  suspend fun updatePhoto(photo: Uri) {
    val phoneNumber = phoneStorage.getPhoneRequired()

    val body = photo.toRequestBody(context)

    val filePart =
      MultipartBody.Part.createFormData(
        /* name = */ "image",
        /* filename = */ "image$phoneNumber",
        /* body = */ body,
      )

    profileApi
      .updatePhoto(
        phoneNumber = phoneNumber,
        image = filePart,
      )
      .throwExceptionIfError()

    updateProfileCache()
  }
  suspend fun updateProfile(profile: Profile) {
    val phoneNumber = phoneStorage.getPhoneRequired()

    profileApi
      .updateProfile(
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
      .throwExceptionIfError()

    updateProfileCache()
  }

  private suspend fun updateProfileCache() {
    val phoneNumber = phoneStorage.getPhoneRequired()

    profileCache.emit(profileApi.getProfile(phoneNumber))
  }
}
