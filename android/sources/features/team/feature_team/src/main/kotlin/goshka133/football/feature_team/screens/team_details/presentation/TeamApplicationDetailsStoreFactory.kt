package goshka133.football.feature_team.screens.team_details.presentation

import goshka133.football.core_elmslie.StoreFactory
import goshka133.football.domain_team.TeamApplication
import goshka133.football.feature_team.screens.team_details.presentation.TeamApplicationDetailsEffect as Effect
import goshka133.football.feature_team.screens.team_details.presentation.TeamApplicationDetailsEvent as Event
import goshka133.football.feature_team.screens.team_details.presentation.TeamApplicationDetailsState as State
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
