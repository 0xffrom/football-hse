package andryuh.football.feature_search.screens.search_player_application.presentation

import androidx.compose.runtime.Immutable
import andryuh.football.core_models.PlayerPosition
import andryuh.football.core_models.Tournament

@Immutable
internal data class SearchPlayerApplicationState(
  val isLoading: Boolean = false,
  val positionsStage: Stage.Positions = Stage.Positions(),
  val tournamentsStage: Stage.Tournaments = Stage.Tournaments(),
  val currentPageType: Stage.Type = Stage.Type.Positions,
) {

  val currentPage
    get() =
      when (currentPageType) {
        Stage.Type.Positions -> positionsStage
        Stage.Type.Tournaments -> tournamentsStage
      }

  @Immutable
  sealed interface Stage {

    val errorMessage: String?

    data class Positions(
      val positions: Map<PlayerPosition, Boolean> = PlayerPosition.values().associateWith { false }
    ) : Stage {

      override val errorMessage: String?
        get() = "Выберите хотя бы одну позицию".takeIf { positions.values.all { !it } }

      val positionsList = positions.entries.toList()
    }

    data class Tournaments(
      val tournaments: Map<Tournament, Boolean> = Tournament.values().associateWith { false }
    ) : Stage {

      override val errorMessage: String?
        get() = "Выберите хотя бы один турнир".takeIf { tournaments.values.all { !it } }

      val tournamentsList = tournaments.entries.toList()
    }

    enum class Type {
      Positions,
      Tournaments,
    }
  }
}

internal sealed interface SearchPlayerApplicationEvent {

  sealed interface Ui : SearchPlayerApplicationEvent {

    sealed interface System : Ui {
      object Start : System
    }

    sealed interface Click : Ui {

      object Back : Ui

      data class OnPositionClick(val playerPosition: PlayerPosition) : Ui
      data class OnTournamentClick(val tournament: Tournament) : Ui

      object Continue: Ui
    }
  }

  sealed interface Internal : SearchPlayerApplicationEvent {

    object CreateTeamApplicationSuccess : Internal
    data class CreateTeamApplicationError(val error: Throwable) : Internal
  }
}

internal sealed interface SearchPlayerApplicationCommand {

  data class CreateTeamApplication(
    val positions: List<PlayerPosition>,
    val tournaments: List<Tournament>,
  ) : SearchPlayerApplicationCommand
}

@Immutable
internal sealed interface SearchPlayerApplicationEffect {

  object Close : SearchPlayerApplicationEffect
  data class ShowError(val error: Throwable) : SearchPlayerApplicationEffect
}
