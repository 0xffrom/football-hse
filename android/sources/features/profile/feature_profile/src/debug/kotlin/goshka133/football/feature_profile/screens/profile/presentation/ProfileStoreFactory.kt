package goshka133.football.feature_profile.screens.profile.presentation

import goshka133.football.core_elmslie.StoreFactory
import goshka133.football.feature_profile.screens.profile.presentation.ProfileEffect as Effect
import goshka133.football.feature_profile.screens.profile.presentation.ProfileEvent as Event
import goshka133.football.feature_profile.screens.profile.presentation.ProfileState as State
import javax.inject.Inject
import vivid.money.elmslie.core.store.Store
import vivid.money.elmslie.coroutines.ElmStoreCompat

internal class ProfileStoreFactory
@Inject
constructor(
  private val actor: ProfileActor,
) : StoreFactory {

  fun create(): Store<Event, Effect, State> =
    ElmStoreCompat(
      startEvent = Event.Ui.System.Start,
      initialState = State(),
      reducer = ProfileReducer,
      actor = actor,
    )
}
