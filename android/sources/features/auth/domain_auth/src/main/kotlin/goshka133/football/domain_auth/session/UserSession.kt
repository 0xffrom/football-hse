package goshka133.football.domain_auth.session

data class UserSession(
  val accessToken: String,
  val phoneNumber: String,
  val isCaptain: Boolean,
  val isRegistered: Boolean,
)

sealed interface UserSessionResponse {

  data class Data(val session: UserSession) : UserSessionResponse

  sealed interface Error : UserSessionResponse {

    object IncorrectCode : Error
  }
}
