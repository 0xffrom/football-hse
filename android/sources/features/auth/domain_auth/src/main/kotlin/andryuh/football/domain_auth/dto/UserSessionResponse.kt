package andryuh.football.domain_auth.dto

import andryuh.football.core_auth.session.UserSession

sealed interface UserSessionResponse {

  data class Data(
    val session: UserSession,
    val shouldBeOnboarded: Boolean = false,
  ) : UserSessionResponse

  sealed interface Error : UserSessionResponse {

    object IncorrectCode : Error
  }
}
