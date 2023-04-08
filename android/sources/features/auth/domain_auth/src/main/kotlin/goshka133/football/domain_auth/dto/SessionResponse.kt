package goshka133.football.domain_auth.dto

import kotlinx.serialization.Serializable

@Serializable
data class SessionResponse(
  val token: String,
  val refreshToken: String,
  val phoneNumber: String,
  val isCaptain: Boolean,
  val isRegistered: Boolean,
)
