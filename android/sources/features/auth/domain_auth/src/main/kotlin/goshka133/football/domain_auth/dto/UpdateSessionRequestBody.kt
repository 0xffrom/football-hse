package goshka133.football.domain_auth.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateSessionRequestBody(
  val refreshToken: String,
  val phoneNumber: String,
)
