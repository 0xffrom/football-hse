package goshka133.football.domain_profile

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import goshka133.football.core_kotlin.nullIfBlank
import goshka133.football.domain_profile.dto.Profile
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
import retrofit2.HttpException

@Singleton
class ProfileRepository
@Inject
constructor(
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

  suspend fun updateProfile(profile: Profile) {
    val phoneNumber = dataStore.data.first()[phoneNumberPrefsKey]!!

    val updateResult = profileApi.updateProfile(
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

    if(!updateResult.isSuccessful){
      throw HttpException(updateResult)
    }
    updateProfileCache()
  }

  private suspend fun updateProfileCache() {
    val phoneNumber = dataStore.data.first()[phoneNumberPrefsKey]!!

    profileCache.emit(profileApi.getProfile(phoneNumber))
  }
}
