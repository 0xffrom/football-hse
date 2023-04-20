package andryuh.football.feature_search.screens.search_player.presentation

import andryuh.football.core_kotlin.Resource
import andryuh.football.feature_search.screens.search_player.presentation.SearchPlayerCommand as Command
import andryuh.football.feature_search.screens.search_player.presentation.SearchPlayerEffect as Effect
import andryuh.football.feature_search.screens.search_player.presentation.SearchPlayerEvent as Event
import andryuh.football.feature_search.screens.search_player.presentation.SearchPlayerEvent.Internal
import andryuh.football.feature_search.screens.search_player.presentation.SearchPlayerEvent.Ui
import andryuh.football.feature_search.screens.search_player.presentation.SearchPlayerState as State
import andryuh.football.ui_kit.error.SomethingWentWrongException
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object SearchPlayerReducer :
  ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

  override fun Result.ui(event: Ui) {
    when (event) {
      is Ui.System.Start -> {
        commands {
          +Command.ObservePlayerApplications
          +Command.ObserveFilter
        }
      }
      is Ui.Action.OnSearchTextFieldValueChange -> {
        state { copy(searchTextFieldValue = event.value) }
        applySearchFilter()
      }
      is Ui.Click.Filter -> {
        effects { +Effect.OpenFilters }
      }
      is Ui.Click.CreateSearchPlayerApplicationBanner -> {
        effects { +Effect.OpenSearchPlayerApplication }
      }
      is Ui.Click.PlayerApplicationCard -> {
        effects { +Effect.OpenPlayerApplicationDetails(event.application) }
      }
    }
  }

  override fun Result.internal(event: Internal) {
    when (event) {
      is Internal.ObservePlayerApplicationsSuccess -> {
        state {
          copy(
            applications = Resource.Data(event.applications),
          )
        }
        applySearchFilter()
      }
      is Internal.ObservePlayerApplicationsError -> {
        if (state.applications !is Resource.Loading) {
          state { copy(applications = Resource.Error(event.error)) }
        } else {
          effects { +Effect.ShowError(SomethingWentWrongException()) }
        }
      }
      is Internal.ObserveFilterSuccess -> {
        state { copy(filter = event.filter) }
        applySearchFilter()
      }
    }
  }

  private fun Result.applySearchFilter() {
    val applications =
      state.applications.value.orEmpty().filter { application ->
        application.footballPosition.intersect(state.filter.positions.toSet()).isNotEmpty() &&
          application.preferredTournaments.intersect(state.filter.tournaments.toSet()).isNotEmpty()
      }

    state {
      copy(
        filteredApplications =
          if (state.searchTextFieldValue.text.isNotBlank()) {
            applications.filter { teamApplication ->
              teamApplication.name
                .orEmpty()
                .lowercase()
                .contains(state.searchTextFieldValue.text.lowercase())
            }
          } else applications,
      )
    }
  }
}
