package goshka133.football.feature_auth.screens.auth.presentation

import goshka133.football.core_elmslie.StoreFactory
import goshka133.football.feature_auth.screens.auth.presentation.AuthEffect as Effect
import goshka133.football.feature_auth.screens.auth.presentation.AuthEvent as Event
import goshka133.football.feature_auth.screens.auth.presentation.AuthState as State
import javax.inject.Inject
import vivid.money.elmslie.core.store.Store
import vivid.money.elmslie.coroutines.ElmStoreCompat

internal class AuthStoreFactory
@Inject
constructor(
  private val actor: AuthActor,
) : StoreFactory {

  fun create(): Store<Event, Effect, State> =
    ElmStoreCompat(
      startEvent = Event.Ui.System.Start,
      initialState = State(),
      reducer = AuthReducer,
      actor = actor,
    )
}
