package andryuh.football.feature_profile.screens.profile_application.presentation

import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.domain_profile.dto.PlayerApplication
import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationEffect as Effect
import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationEvent as Event
import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationState as State
import javax.inject.Inject
import vivid.money.elmslie.core.store.Store
import vivid.money.elmslie.coroutines.ElmStoreCompat

internal class ProfileApplicationStoreFactory
@Inject
constructor(
  private val actor: ProfileApplicationActor,
) : StoreFactory {

  fun create(profileApplication: PlayerApplication): Store<Event, Effect, State> =
    ElmStoreCompat(
      startEvent = Event.Ui.System.Start,
      initialState =
        State(
          playerApplication = profileApplication,
        ),
      reducer = ProfileApplicationReducer,
      actor = actor,
    )
}
