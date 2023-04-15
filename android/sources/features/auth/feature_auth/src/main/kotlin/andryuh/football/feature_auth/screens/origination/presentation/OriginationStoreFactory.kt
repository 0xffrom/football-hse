package andryuh.football.feature_auth.screens.origination.presentation

import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.feature_auth.screens.origination.presentation.OriginationEffect as Effect
import andryuh.football.feature_auth.screens.origination.presentation.OriginationEvent as Event
import andryuh.football.feature_auth.screens.origination.presentation.OriginationState as State
import javax.inject.Inject
import vivid.money.elmslie.core.store.Store
import vivid.money.elmslie.coroutines.ElmStoreCompat

internal class OriginationStoreFactory
@Inject
constructor(
  private val actor: OriginationActor,
) : StoreFactory {

  fun create(): Store<Event, Effect, State> =
    ElmStoreCompat(
      startEvent = Event.Ui.System.Start,
      initialState = State(),
      reducer = OriginationReducer,
      actor = actor,
    )
}
