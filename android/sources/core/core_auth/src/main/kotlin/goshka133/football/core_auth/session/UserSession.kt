package goshka133.football.core_auth.session

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSession(
  @SerialName("token") val accessToken: String,
  val refreshToken: String,
  val phoneNumber: String,
  val isCaptain: Boolean,
  val isRegistered: Boolean,
)
