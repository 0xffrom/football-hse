package andryuh.football.feature_search.screens.search.presentation

import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.feature_search.screens.search.presentation.SearchEffect as Effect
import andryuh.football.feature_search.screens.search.presentation.SearchEvent as Event
import andryuh.football.feature_search.screens.search.presentation.SearchState as State
import javax.inject.Inject
import vivid.money.elmslie.core.store.Store
import vivid.money.elmslie.coroutines.ElmStoreCompat

internal class SearchStoreFactory
@Inject
constructor(
  private val actor: SearchActor,
) : StoreFactory {

  fun create(): Store<Event, Effect, State> =
    ElmStoreCompat(
      startEvent = Event.Ui.System.Start,
      initialState = State(),
      reducer = SearchReducer,
      actor = actor,
    )
}
