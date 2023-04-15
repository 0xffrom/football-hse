package andryuh.football.domain_team

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(
  val id: String,
  val name: String,
  val captainPhoneNumber: String,
  val captainName: String,
  val logo: String?,
  val about: String?,
  val status: TeamStatus,
) : Parcelable

enum class TeamStatus {
  OnValidation,
  Verified,
  Declined,
}
