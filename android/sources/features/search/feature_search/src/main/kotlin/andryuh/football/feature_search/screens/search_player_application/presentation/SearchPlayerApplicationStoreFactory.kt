package andryuh.football.feature_search.screens.search_player_application.presentation

import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.feature_search.screens.search_player_application.presentation.SearchPlayerApplicationEffect as Effect
import andryuh.football.feature_search.screens.search_player_application.presentation.SearchPlayerApplicationEvent as Event
import andryuh.football.feature_search.screens.search_player_application.presentation.SearchPlayerApplicationState as State
import javax.inject.Inject
import vivid.money.elmslie.core.store.Store
import vivid.money.elmslie.coroutines.ElmStoreCompat

internal class SearchPlayerApplicationStoreFactory
@Inject
constructor(
  private val actor: SearchPlayerApplicationActor,
) : StoreFactory {

  fun create(): Store<Event, Effect, State> =
    ElmStoreCompat(
      startEvent = Event.Ui.System.Start,
      initialState = State(),
      reducer = SearchPlayerApplicationReducer,
      actor = actor,
    )
}
