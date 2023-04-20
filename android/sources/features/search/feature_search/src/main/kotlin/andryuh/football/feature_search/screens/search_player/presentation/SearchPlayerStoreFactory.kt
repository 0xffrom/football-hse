package andryuh.football.feature_search.screens.search_player.presentation

import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.feature_search.screens.search_player.presentation.SearchPlayerEffect as Effect
import andryuh.football.feature_search.screens.search_player.presentation.SearchPlayerEvent as Event
import andryuh.football.feature_search.screens.search_player.presentation.SearchPlayerState as State
import javax.inject.Inject
import vivid.money.elmslie.core.store.Store
import vivid.money.elmslie.coroutines.ElmStoreCompat

internal class SearchPlayerStoreFactory
@Inject
constructor(
  private val actor: SearchPlayerActor,
) : StoreFactory {

  fun create(): Store<Event, Effect, State> =
    ElmStoreCompat(
      startEvent = Event.Ui.System.Start,
      initialState = State(),
      reducer = SearchPlayerReducer,
      actor = actor,
    )
}
