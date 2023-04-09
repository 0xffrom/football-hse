package goshka133.football.core_auth.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserSessionRequestBody(
  val refreshToken: String,
  val phoneNumber: String,
)
