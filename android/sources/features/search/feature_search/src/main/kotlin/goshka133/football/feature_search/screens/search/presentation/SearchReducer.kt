package goshka133.football.feature_search.screens.search.presentation

import goshka133.football.feature_search.screens.search.presentation.SearchCommand as Command
import goshka133.football.feature_search.screens.search.presentation.SearchEffect as Effect
import goshka133.football.feature_search.screens.search.presentation.SearchEvent as Event
import goshka133.football.feature_search.screens.search.presentation.SearchEvent.Internal
import goshka133.football.feature_search.screens.search.presentation.SearchEvent.Ui
import goshka133.football.feature_search.screens.search.presentation.SearchState as State
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
                  teamApplication.title.lowercase().contains(event.value.text.lowercase())
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
    }
  }

  override fun Result.internal(event: Internal) = Unit
}
