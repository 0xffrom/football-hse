package goshka133.football.domain_auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import goshka133.football.core_auth.session.UserSessionUpdater
import goshka133.football.domain_auth.dto.UserSessionResponse
import goshka133.football.domain_profile.ProfileApi
import goshka133.football.domain_profile.dto.CreateProfileBody
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import retrofit2.HttpException

class AuthRepository
@Inject
constructor(
  private val api: AuthApi,
  private val profileApi: ProfileApi,
  private val dataStore: DataStore<Preferences>,
  private val userSessionUpdater: UserSessionUpdater,
) {

  private val phoneNumberPrefsKey = stringPreferencesKey("phoneNumber")

  suspend fun sendOtp(phoneNumber: String) {
    dataStore.edit { prefs -> prefs[phoneNumberPrefsKey] = phoneNumber }
    api.sendOtpCode(phoneNumber)
  }

  suspend fun verifyOtpCode(code: String): UserSessionResponse {
    val phoneNumber =
      dataStore.data.first()[phoneNumberPrefsKey]
        ?: throw IllegalStateException("Empty phone number in the preferences")

    val response =
      runCatching {
          val session = api.verifyOtpCode(phoneNumber = phoneNumber, code = code)
          userSessionUpdater.updateSession(session)

          UserSessionResponse.Data(session)
        }
        .getOrElse { error ->
          when {
            error is HttpException && error.code() == 400 -> UserSessionResponse.Error.IncorrectCode
            else -> throw error
          }
        }

    if (response is UserSessionResponse.Data) {
      if (!response.session.isRegistered) {
        val profile = profileApi.getProfile(response.session.phoneNumber)

        return response.copy(
          shouldBeOnboarded = profile.fullName.isNullOrBlank(),
        )
      }
    }

    return response
  }
}
