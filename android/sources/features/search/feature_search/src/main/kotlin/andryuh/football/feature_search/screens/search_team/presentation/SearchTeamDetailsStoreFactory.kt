package andryuh.football.feature_search.screens.search_team.presentation

import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.feature_search.screens.search_team.presentation.SearchTeamDetailsEffect as Effect
import andryuh.football.feature_search.screens.search_team.presentation.SearchTeamDetailsEvent as Event
import andryuh.football.feature_search.screens.search_team.presentation.SearchTeamDetailsState as State
import javax.inject.Inject
import vivid.money.elmslie.core.store.Store
import vivid.money.elmslie.coroutines.ElmStoreCompat

internal class SearchTeamDetailsStoreFactory
@Inject
constructor(
  private val actor: SearchTeamDetailsActor,
) : StoreFactory {

  fun create(): Store<Event, Effect, State> =
    ElmStoreCompat(
      startEvent = Event.Ui.System.Start,
      initialState = State(),
      reducer = SearchTeamDetailsReducer,
      actor = actor,
    )
}
