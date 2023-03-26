package goshka133.football.feature_profile.screens.team_registration.presentation

import goshka133.football.core_elmslie.StoreFactory
import goshka133.football.domain_profile.dto.Profile
import goshka133.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEffect as Effect
import goshka133.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEvent as Event
import goshka133.football.feature_profile.screens.team_registration.presentation.TeamRegistrationState as State
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
