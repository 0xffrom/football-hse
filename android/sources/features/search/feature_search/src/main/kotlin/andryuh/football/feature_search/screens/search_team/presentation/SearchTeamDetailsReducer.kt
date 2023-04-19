package andryuh.football.feature_search.screens.search_team.presentation

import andryuh.football.feature_search.screens.search_team.presentation.SearchTeamDetailsCommand as Command
import andryuh.football.feature_search.screens.search_team.presentation.SearchTeamDetailsEffect as Effect
import andryuh.football.feature_search.screens.search_team.presentation.SearchTeamDetailsEvent as Event
import andryuh.football.feature_search.screens.search_team.presentation.SearchTeamDetailsEvent.Internal
import andryuh.football.feature_search.screens.search_team.presentation.SearchTeamDetailsEvent.Ui
import andryuh.football.feature_search.screens.search_team.presentation.SearchTeamDetailsState as State
import andryuh.football.ui_kit.error.SomethingWentWrongException
import java.lang.IllegalStateException
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object SearchTeamDetailsReducer :
  ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

  override fun Result.ui(event: Ui) {
    when (event) {
      is Ui.System.Start -> Unit
      is Ui.Click.OnPositionClick -> {
        state {
          copy(
            positionsStage =
              positionsStage.copy(
                positions =
                  positionsStage.positions.mapValues { (position, value) ->
                    if (position == event.playerPosition) {
                      !value
                    } else {
                      value
                    }
                  }
              )
          )
        }
      }
      is Ui.Click.OnTournamentClick -> {
        state {
          copy(
            tournamentsStage =
              tournamentsStage.copy(
                tournaments =
                  tournamentsStage.tournaments.mapValues { (tournament, value) ->
                    if (tournament == event.tournament) {
                      !value
                    } else {
                      value
                    }
                  }
              )
          )
        }
      }
      is Ui.Click.Continue -> {
        val errorMessage: String? = state.currentPage.errorMessage

        if (errorMessage != null) {
          effects { +Effect.ShowError(IllegalStateException(errorMessage)) }
        } else {
          when (state.currentPage) {
            is State.Stage.Positions -> {
              state {
                copy(
                  currentPageType = State.Stage.Type.Tournaments,
                )
              }
            }
            is State.Stage.Tournaments -> {
              state {
                copy(
                  isLoading = true,
                )
              }
              commands {
                +Command.CreatePlayerApplication(
                  positions =
                    state.positionsStage.positions.mapNotNull { (key, value) ->
                      if (value) key else null
                    },
                  tournaments =
                    state.tournamentsStage.tournaments.mapNotNull { (key, value) ->
                      if (value) key else null
                    },
                )
              }
            }
          }
        }
      }
      is Ui.Click.Back -> {
        when (state.currentPage) {
          is State.Stage.Positions -> {
            effects { +Effect.Close }
          }
          is State.Stage.Tournaments -> {
            state { copy(currentPageType = State.Stage.Type.Positions) }
          }
        }
      }
    }
  }

  override fun Result.internal(event: Internal) {
    when (event) {
      is Internal.CreatePlayerApplicationSuccess -> {
        state {
          copy(
            isLoading = false,
          )
        }
        effects { +Effect.Close }
      }
      is Internal.CreatePlayerApplicationError -> {
        state {
          copy(
            isLoading = false,
          )
        }
        effects { +Effect.ShowError(SomethingWentWrongException()) }
      }
    }
  }
}
