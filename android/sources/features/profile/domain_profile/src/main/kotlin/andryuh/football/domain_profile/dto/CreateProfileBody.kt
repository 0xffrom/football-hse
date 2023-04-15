package andryuh.football.domain_profile.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateProfileBody(
  val phoneNumber: String,
)
