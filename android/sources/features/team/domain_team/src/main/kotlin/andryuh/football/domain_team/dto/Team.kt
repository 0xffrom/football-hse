package andryuh.football.domain_team.dto

import android.os.Parcelable
import andryuh.football.core_network.serializers.EnumAsIntSerializer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Team(
  val id: String,
  val name: String,
  val captainPhoneNumber: String,
  val captainName: String,
  val logo: String?,
  val about: String?,
  val status: TeamStatus,
) : Parcelable

@Serializable(with = TeamStatusSerializer::class)
enum class TeamStatus(val value: Int) {
  OnValidation(0),
  Verified(1),
  Declined(2),
}

private class TeamStatusSerializer :
  EnumAsIntSerializer<TeamStatus>(
    "status",
    { it.value },
    { v -> TeamStatus.values().first { it.value == v } }
  )
