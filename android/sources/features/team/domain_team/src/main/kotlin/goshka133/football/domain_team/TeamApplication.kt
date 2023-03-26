package goshka133.football.domain_team

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import goshka133.football.core_models.PlayerPosition
import goshka133.football.core_models.Tournament
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class TeamApplication(
  val id: String,
  val teamId: String,
  val name: String,
  val imageUrl: String?,
  val description: String?,
  val contact: String?,
  val playerPosition: List<PlayerPosition>,
  val tournaments: List<Tournament>,
) : Parcelable {

  companion object
}

fun TeamApplication.Companion.mockList(): List<TeamApplication> {
  return buildList {
    val contact = "По всем вопросам писать в личные сообщения: https://vk.com/id0000000000"
    val description =
      "Как команда, мы предлагаем:\n" +
        "• регулярные тренировки с тренером на полях спорткомплекса “Лужники”\n" +
        "• стремление прогрессировать, занимать первые места во всех турнирах под эгидой чемпионата Вышки, так мы прошли за полтора года путь от низших мест Молодёжной Лиги до верхового футбола Вышки, и выиграли уже 4 комплекта медалей\n" +
        "• самый молодой коллектив в верховом футболе НИУ ВШЭ, при этом дружный и амбициозный"
    add(
      TeamApplication(
        id = "1",
        teamId = "1",
        name = "ФК Неизвестные",
        imageUrl = "https://i.ibb.co/vY69NRW/Group-14.png",
        playerPosition = listOf(PlayerPosition.Goalkeeper, PlayerPosition.RightMidfielder),
        tournaments =
          listOf(Tournament.SecondLeague, Tournament.MajorLeague, Tournament.FutsalPremierLeague),
        contact = contact,
        description = description,
      )
    )
    add(
      TeamApplication(
        id = "2",
        teamId = "2",
        name = "ФК Известные",
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
        contact = contact,
        description = description,
      )
    )
    add(
      TeamApplication(
        id = "3",
        teamId = "3",
        name = "ФК Хсе",
        imageUrl = "https://i.ibb.co/3v3m3W1/Group-14-2.png",
        playerPosition = listOf(PlayerPosition.LeftDefender),
        tournaments =
          listOf(
            Tournament.SecondLeague,
          ),
        contact = contact,
        description = description,
      )
    )
    add(
      TeamApplication(
        id = "4",
        teamId = "4",
        name = "ФК Спартак",
        imageUrl = "https://i.ibb.co/vY69NRW/Group-14.png",
        playerPosition = listOf(PlayerPosition.Goalkeeper),
        tournaments = listOf(Tournament.SecondLeague),
        contact = contact,
        description = description,
      )
    )
    add(
      TeamApplication(
        id = "5",
        teamId = "5",
        name = "ФК Зенит",
        imageUrl = "https://i.ibb.co/gtHfQJ5/Group-14-1.png",
        playerPosition =
          listOf(
            PlayerPosition.LeftMidfielder,
          ),
        tournaments =
          listOf(
            Tournament.FutsalFirstLeague,
          ),
        contact = contact,
        description = description,
      )
    )
    add(
      TeamApplication(
        id = "6",
        teamId = "6",
        name = "ФК МГУ",
        imageUrl = "https://i.ibb.co/3v3m3W1/Group-14-2.png",
        playerPosition = listOf(PlayerPosition.CentralAttackingMidfielder),
        tournaments =
          listOf(
            Tournament.SummerCup,
            Tournament.AutumnCup,
          ),
        contact = contact,
        description = description,
      )
    )
  }
}
