package andryuh.football.domain_team.dto

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import andryuh.football.core_models.PlayerPosition
import andryuh.football.core_models.Tournament
import andryuh.football.core_network.serializers.EnumListAsIntSerializer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Parcelize
@Serializable
data class TeamApplication(
  val id: String?,
  val teamId: String,
  val name: String,
  @SerialName("logo") val imageUrl: String?,
  val description: String?,
  val contact: String?,
  @Serializable(with = PlayerPositionsSerializer::class) val playerPosition: List<PlayerPosition>,
  @Serializable(with = TournamentsSerializer::class) val tournaments: List<Tournament>,
) : Parcelable

@Immutable
@Parcelize
@Serializable
data class TeamApplicationCreation(
  val teamId: String,
  val name: String,
  @SerialName("logo") val imageUrl: String?,
  val description: String?,
  val contact: String?,
  @Serializable(with = PlayerPositionsSerializer::class) val playerPosition: List<PlayerPosition>,
  @Serializable(with = TournamentsSerializer::class) val tournaments: List<Tournament>,
) : Parcelable

internal class PlayerPositionsSerializer :
  EnumListAsIntSerializer<PlayerPosition>(
    serialName = "playerPosition",
    enumArray = PlayerPosition.values(),
  )

internal class TournamentsSerializer :
  EnumListAsIntSerializer<Tournament>(
    serialName = "tournaments",
    enumArray = Tournament.values(),
  )
