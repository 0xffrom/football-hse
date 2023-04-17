package andryuh.football.feature_team.screens.team_application_details.presentation

import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.domain_team.dto.TeamApplication
import andryuh.football.feature_team.screens.team_application_details.presentation.TeamApplicationDetailsEffect as Effect
import andryuh.football.feature_team.screens.team_application_details.presentation.TeamApplicationDetailsEvent as Event
import andryuh.football.feature_team.screens.team_application_details.presentation.TeamApplicationDetailsState as State
import javax.inject.Inject
import vivid.money.elmslie.core.store.Store
import vivid.money.elmslie.coroutines.ElmStoreCompat

internal class TeamApplicationDetailsStoreFactory
@Inject
constructor(
  private val actor: TeamApplicationDetailsActor,
) : StoreFactory {

  fun create(application: TeamApplication): Store<Event, Effect, State> =
    ElmStoreCompat(
      startEvent = Event.Ui.System.Start,
      initialState = State(
        teamApplication = application,
      ),
      reducer = TeamApplicationDetailsReducer,
      actor = actor,
    )
}
