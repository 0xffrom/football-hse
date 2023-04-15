package andryuh.football.feature_search.screens.search.presentation

import andryuh.football.feature_search.screens.search.presentation.SearchCommand as Command
import andryuh.football.feature_search.screens.search.presentation.SearchEffect as Effect
import andryuh.football.feature_search.screens.search.presentation.SearchEvent as Event
import andryuh.football.feature_search.screens.search.presentation.SearchEvent.Internal
import andryuh.football.feature_search.screens.search.presentation.SearchEvent.Ui
import andryuh.football.feature_search.screens.search.presentation.SearchState as State
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object SearchReducer :
  ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

  override fun Result.ui(event: Ui) {
    when (event) {
      is Ui.System.Start -> {
        // your code
      }
      is Ui.Action.OnSearchTextFieldValueChange -> {
        state {
          copy(
            searchTextFieldValue = event.value,
            filteredApplications =
              if (event.value.text.isNotBlank()) {
                state.applications.filter { teamApplication ->
                  teamApplication.name.lowercase().contains(event.value.text.lowercase())
                }
              } else state.applications
          )
        }
      }
      is Ui.Click.Filter -> {
        // TODO: Implement this event.
      }
      is Ui.Click.CreateApplicationBanner -> {
        // TODO: Implement this event.
      }
      is Ui.Click.TeamApplicationCard -> {
        effects {
          +Effect.OpenTeamApplicationDetails(event.application)
        }
      }
    }
  }

  override fun Result.internal(event: Internal) = Unit
}
