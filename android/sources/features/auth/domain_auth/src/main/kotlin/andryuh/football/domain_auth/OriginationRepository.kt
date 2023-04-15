package andryuh.football.domain_auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import andryuh.football.domain_profile.ProfileApi
import andryuh.football.domain_profile.dto.RoleType
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import retrofit2.HttpException

class OriginationRepository
@Inject
constructor(
  private val profileApi: ProfileApi,
  private val dataStore: DataStore<Preferences>,
) {

  private val phoneNumberPrefsKey = stringPreferencesKey("phoneNumber")

  suspend fun updateProfile(
    fullName: String,
    role: RoleType,
  ) {
    val phoneNumber = requireNotNull(dataStore.data.first()[phoneNumberPrefsKey])

    val profile = profileApi.getProfile(phoneNumber)

    val updateResponse =
      profileApi.updateProfile(
        phoneNumber = phoneNumber,
        profileBody =
          profile.copy(
            fullName = fullName,
            role = role,
          )
      )

    if (!updateResponse.isSuccessful) {
      throw HttpException(updateResponse)
    }
  }
}
