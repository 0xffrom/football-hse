package andryuh.football.feature_search.screens.filters.presentation

import androidx.compose.runtime.Immutable
import andryuh.football.core_models.PlayerPosition
import andryuh.football.core_models.Tournament
import andryuh.football.domain_search.filters.Filter

@Immutable
internal data class SearchFiltersState(
  val isLoading: Boolean = false,
  val initialFilter: Filter,
  val positionsStage: Stage.Positions = Stage.Positions(initialFilter),
  val tournamentsStage: Stage.Tournaments = Stage.Tournaments(initialFilter),
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
      val initialFilter: Filter,
      val positions: Map<PlayerPosition, Boolean> =
        PlayerPosition.values().associateWith { position -> position in initialFilter.positions }
    ) : Stage {

      override val errorMessage: String?
        get() = "Выберите хотя бы одну позицию".takeIf { positions.values.all { !it } }

      val positionsList = positions.entries.toList()
    }

    data class Tournaments(
      val initialFilter: Filter,
      val tournaments: Map<Tournament, Boolean> =
        Tournament.values().associateWith { tournament -> tournament in initialFilter.tournaments }
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

internal sealed interface SearchFiltersEvent {

  sealed interface Ui : SearchFiltersEvent {
    sealed interface System : Ui {
      object Start : System
    }

    sealed interface Click : Ui {
      object Back : Ui

      data class OnPositionClick(val playerPosition: PlayerPosition) : Ui
      data class OnTournamentClick(val tournament: Tournament) : Ui

      object Continue : Ui
    }

    sealed interface Action : Ui {
      // your code
    }
  }

  sealed interface Internal : SearchFiltersEvent {

    object SaveFilterSuccess : Internal
  }
}

internal sealed interface SearchFiltersCommand {

  data class SaveFilter(val filter: Filter) : SearchFiltersCommand
}

@Immutable
internal sealed interface SearchFiltersEffect {

  object Close : SearchFiltersEffect
  data class ShowError(val error: Throwable) : SearchFiltersEffect
}
