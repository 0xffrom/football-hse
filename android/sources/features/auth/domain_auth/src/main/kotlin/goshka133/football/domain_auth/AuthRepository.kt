package goshka133.football.domain_auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import goshka133.football.domain_auth.session.UserSession
import goshka133.football.domain_auth.session.UserSessionResponse
import goshka133.football.domain_auth.session.UserSessionUpdater
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import retrofit2.HttpException

class AuthRepository
@Inject
constructor(
  private val api: AuthApi,
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

    return runCatching {
        val response = api.verifyOtpCode(phoneNumber = phoneNumber, code = code)

        userSessionUpdater.updateSession(response)
      UserSessionResponse.Data(
        session =
        UserSession(
          accessToken = response.token,
          phoneNumber = response.phoneNumber,
          isCaptain = response.isCaptain,
          isRegistered = response.isRegistered,
        )
      )
      }
      .getOrElse { error ->
        when {
          error is HttpException && error.code() == 400 -> UserSessionResponse.Error.IncorrectCode
          else -> throw error
        }
      }
  }
}
