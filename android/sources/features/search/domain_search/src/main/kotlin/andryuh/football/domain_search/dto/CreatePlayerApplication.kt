package andryuh.football.domain_search.dto

import andryuh.football.core_models.PlayerPosition
import andryuh.football.core_models.Tournament
import andryuh.football.core_network.serializers.EnumListAsIntSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePlayerApplication(
  @SerialName("playerPhoneNumber") val phoneNumber: String,
  @Serializable(with = PlayerPositionsSerializer::class) val footballPosition: List<PlayerPosition>,
  @Serializable(with = TournamentsSerializer::class) val preferredTournaments: List<Tournament>,
  val footballExperience: String?,
  @SerialName("tournamentExperience") val tournamentsExperience: String?,
  val contact: String?,
  val name: String?,
  @SerialName("photo") val imageUrl: String?
)

internal class PlayerPositionsSerializer :
  EnumListAsIntSerializer<PlayerPosition>(
    serialName = "footballPosition",
    enumArray = PlayerPosition.values(),
  )

internal class TournamentsSerializer :
  EnumListAsIntSerializer<Tournament>(
    serialName = "preferredTournaments",
    enumArray = Tournament.values(),
  )
