package andryuh.football.domain_team.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Suppress("DataClassPrivateConstructor")
@Serializable
data class CreateTeamRequestBody
private constructor(
  @SerialName("name") val name: String,
  @SerialName("captainName") val captainName: String,
  @SerialName("captainPhoneNumber") val captainPhoneNumber: String,
  @SerialName("about") val aboutInfo: String,
) {

  companion object {

    fun create(body: CreateTeamBody, phoneNumber: String): CreateTeamRequestBody {
      return CreateTeamRequestBody(
        name = body.name,
        captainName = body.captainName,
        aboutInfo = body.aboutInfo,
        captainPhoneNumber = phoneNumber,
      )
    }
  }
}

data class CreateTeamBody(
  val name: String,
  val captainName: String,
  val aboutInfo: String,
)
