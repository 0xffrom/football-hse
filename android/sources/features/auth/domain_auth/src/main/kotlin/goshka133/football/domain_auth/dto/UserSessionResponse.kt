package goshka133.football.domain_auth.dto

import goshka133.football.core_auth.session.UserSession

sealed interface UserSessionResponse {

  data class Data(
    val session: UserSession,
    val shouldBeOnboarded: Boolean = false,
  ) : UserSessionResponse

  sealed interface Error : UserSessionResponse {

    object IncorrectCode : Error
  }
}
