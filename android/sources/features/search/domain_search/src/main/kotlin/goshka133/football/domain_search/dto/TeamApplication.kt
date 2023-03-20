package goshka133.football.domain_search.dto

import androidx.compose.runtime.Immutable
import goshka133.football.core_models.PlayerPosition
import goshka133.football.core_models.Tournament

@Immutable
data class TeamApplication(
  val id: String,
  val title: String,
  val imageUrl: String,
  val playerPosition: List<PlayerPosition>,
  val tournaments: List<Tournament>,
) {

  companion object
}

fun TeamApplication.Companion.mockList(): List<TeamApplication> {
  return buildList {
    add(
      TeamApplication(
        id = "1",
        title = "ФК Неизвестные",
        imageUrl = "https://i.ibb.co/vY69NRW/Group-14.png",
        playerPosition = listOf(PlayerPosition.Goalkeeper, PlayerPosition.RightMidfielder),
        tournaments =
          listOf(Tournament.SecondLeague, Tournament.MajorLeague, Tournament.FutsalPremierLeague),
      )
    )
    add(
      TeamApplication(
        id = "2",
        title = "ФК Известные",
        imageUrl = "https://i.ibb.co/gtHfQJ5/Group-14-1.png",
        playerPosition =
          listOf(
            PlayerPosition.LeftMidfielder,
            PlayerPosition.CentralDefender,
            PlayerPosition.RightDefender,
            PlayerPosition.DefensiveMidfielder,
          ),
        tournaments =
          listOf(
            Tournament.FutsalFirstLeague,
            Tournament.FutsalYouthLeague,
            Tournament.FutsalSecondLeague,
            Tournament.FutsalPremierLeague,
          ),
      )
    )
    add(
      TeamApplication(
        id = "3",
        title = "ФК Хсе",
        imageUrl = "https://i.ibb.co/3v3m3W1/Group-14-2.png",
        playerPosition = listOf(PlayerPosition.LeftDefender),
        tournaments =
          listOf(
            Tournament.SecondLeague,
          ),
      )
    )
    add(
      TeamApplication(
        id = "4",
        title = "ФК Спартак",
        imageUrl = "https://i.ibb.co/vY69NRW/Group-14.png",
        playerPosition = listOf(PlayerPosition.Goalkeeper),
        tournaments = listOf(Tournament.SecondLeague),
      )
    )
    add(
      TeamApplication(
        id = "5",
        title = "ФК Зенит",
        imageUrl = "https://i.ibb.co/gtHfQJ5/Group-14-1.png",
        playerPosition =
          listOf(
            PlayerPosition.LeftMidfielder,
          ),
        tournaments =
          listOf(
            Tournament.FutsalFirstLeague,
          ),
      )
    )
    add(
      TeamApplication(
        id = "6",
        title = "ФК МГУ",
        imageUrl = "https://i.ibb.co/3v3m3W1/Group-14-2.png",
        playerPosition = listOf(PlayerPosition.CentralAttackingMidfielder),
        tournaments =
          listOf(
            Tournament.SummerCup,
            Tournament.AutumnCup,
          ),
      )
    )
  }
}
