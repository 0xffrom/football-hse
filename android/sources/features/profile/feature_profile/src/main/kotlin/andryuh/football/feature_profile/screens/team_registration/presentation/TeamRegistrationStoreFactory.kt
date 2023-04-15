package andryuh.football.feature_profile.screens.team_registration.presentation

import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.domain_profile.dto.Profile
import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEffect as Effect
import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEvent as Event
import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationState as State
import javax.inject.Inject
import vivid.money.elmslie.core.store.Store
import vivid.money.elmslie.coroutines.ElmStoreCompat

internal class TeamRegistrationStoreFactory
@Inject
constructor(
  private val actor: TeamRegistrationActor,
) : StoreFactory {

  fun create(profile: Profile): Store<Event, Effect, State> =
    ElmStoreCompat(
      startEvent = Event.Ui.System.Start,
      initialState = State(profile = profile),
      reducer = TeamRegistrationReducer,
      actor = actor,
    )
}
