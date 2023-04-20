package andryuh.football.domain_profile.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Profile(
  @SerialName("name") val fullName: String?,
  val footballExperience: String?,
  @SerialName("tournamentExperience") val tournamentsExperience: String?,
  @SerialName("contact") val contactInfo: String?,
  val about: String?,
  @SerialName("photo") val imageUrl: String?,
  val isCaptain: Boolean,
  val isRegistered: Boolean,
  val phoneNumber: String,
  @SerialName("hseRole") val role: RoleType,
) : Parcelable
