package andryuh.football.feature_search.screens.filters.presentation

import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.domain_search.filters.Filter
import andryuh.football.domain_search.filters.FilterType
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersEffect as Effect
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersEvent as Event
import andryuh.football.feature_search.screens.filters.presentation.SearchFiltersState as State
import javax.inject.Inject
import vivid.money.elmslie.core.store.Store
import vivid.money.elmslie.coroutines.ElmStoreCompat

internal class SearchFiltersStoreFactory
@Inject
constructor(
  private val actor: SearchFiltersActor,
) : StoreFactory {

  fun create(filter: Filter, type: FilterType): Store<Event, Effect, State> =
    ElmStoreCompat(
      startEvent = Event.Ui.System.Start,
      initialState =
        State(
          initialFilter = filter,
          filterType = type,
        ),
      reducer = SearchFiltersReducer,
      actor = actor,
    )
}
