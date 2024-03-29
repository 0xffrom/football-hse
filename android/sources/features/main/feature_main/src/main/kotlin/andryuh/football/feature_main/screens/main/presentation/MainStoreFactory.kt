package andryuh.football.feature_main.screens.main.presentation

import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.feature_main.screens.main.models.BottomBarTabType
import andryuh.football.feature_main.screens.main.presentation.MainEffect as Effect
import andryuh.football.feature_main.screens.main.presentation.MainEvent as Event
import andryuh.football.feature_main.screens.main.presentation.MainState as State
import javax.inject.Inject
import vivid.money.elmslie.core.store.Store
import vivid.money.elmslie.coroutines.ElmStoreCompat

internal class MainStoreFactory
@Inject
constructor(
  private val actor: MainActor,
) : StoreFactory {

  fun create(): Store<Event, Effect, State> =
    ElmStoreCompat(
      startEvent = Event.Ui.System.Start,
      initialState = State(
        selectedTab = BottomBarTabType.values().first(),
      ),
      reducer = MainReducer,
      actor = actor,
    )
}
