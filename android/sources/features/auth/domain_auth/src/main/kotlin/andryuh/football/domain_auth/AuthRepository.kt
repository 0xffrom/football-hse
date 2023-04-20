package andryuh.football.domain_auth

import andryuh.football.core_auth.PhoneStorage
import andryuh.football.core_auth.session.UserSessionUpdater
import andryuh.football.domain_auth.dto.UserSessionResponse
import andryuh.football.domain_profile.ProfileApi
import javax.inject.Inject
import retrofit2.HttpException

class AuthRepository
@Inject
constructor(
  private val api: AuthApi,
  private val profileApi: ProfileApi,
  private val userSessionUpdater: UserSessionUpdater,
  private val phoneStorage: PhoneStorage,
) {

  suspend fun sendOtp(phoneNumber: String) {
    phoneStorage.updatePhone(phoneNumber)
    api.sendOtpCode(phoneNumber)
  }

  suspend fun clearSession() {
    userSessionUpdater.updateSession(null)
  }

  suspend fun verifyOtpCode(code: String): UserSessionResponse {
    val phoneNumber = phoneStorage.getPhoneRequired()

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
