package andryuh.football.feature_team.screens.team_details.presentation

import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.domain_team.dto.Team
import andryuh.football.feature_team.screens.team_details.presentation.TeamDetailsEffect as Effect
import andryuh.football.feature_team.screens.team_details.presentation.TeamDetailsEvent as Event
import andryuh.football.feature_team.screens.team_details.presentation.TeamDetailsState as State
import javax.inject.Inject
import vivid.money.elmslie.core.store.Store
import vivid.money.elmslie.coroutines.ElmStoreCompat

internal class TeamDetailsStoreFactory
@Inject
constructor(
  private val actor: TeamDetailsActor,
) : StoreFactory {

  fun create(team: Team): Store<Event, Effect, State> =
    ElmStoreCompat(
      startEvent = Event.Ui.System.Start,
      initialState = State(team = team),
      reducer = TeamDetailsReducer,
      actor = actor,
    )
}
