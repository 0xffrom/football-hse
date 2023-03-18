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
    }
  }

  override fun Result.internal(event: Internal) = Unit
}
