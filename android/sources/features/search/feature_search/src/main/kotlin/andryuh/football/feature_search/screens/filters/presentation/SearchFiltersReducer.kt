package andryuh.football.feature_search.screens.filters.presentation

import andryuh.football.domain_search.filters.Filter
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersCommand as Command
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersEffect as Effect
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersEvent as Event
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersEvent.Internal
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersEvent.Ui
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersState as State
import java.lang.IllegalStateException
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object SearchFiltersReducer :
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
                +Command.SaveFilter(
                  Filter(
                    positions =
                      state.positionsStage.positions.mapNotNull { (key, value) ->
                        if (value) key else null
                      },
                    tournaments =
                      state.tournamentsStage.tournaments.mapNotNull { (key, value) ->
                        if (value) key else null
                      },
                  )
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
      is Internal.SaveFilterSuccess -> {
        state {
          copy(
            isLoading = false,
          )
        }
        effects { +Effect.Close }
      }
    }
  }
}
