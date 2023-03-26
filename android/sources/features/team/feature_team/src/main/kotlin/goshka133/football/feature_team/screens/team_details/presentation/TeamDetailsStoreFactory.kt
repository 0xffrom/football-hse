package goshka133.football.feature_team.screens.team_details.presentation

import goshka133.football.core_elmslie.StoreFactory
import goshka133.football.feature_team.screens.team_details.presentation.TeamDetailsEffect as Effect
import goshka133.football.feature_team.screens.team_details.presentation.TeamDetailsEvent as Event
import goshka133.football.feature_team.screens.team_details.presentation.TeamDetailsState as State
import javax.inject.Inject
import vivid.money.elmslie.core.store.Store
import vivid.money.elmslie.coroutines.ElmStoreCompat

internal class TeamDetailsStoreFactory
@Inject
constructor(
  private val actor: TeamDetailsActor,
) : StoreFactory {

  fun create(): Store<Event, Effect, State> =
    ElmStoreCompat(
      startEvent = Event.Ui.System.Start,
      initialState = State(),
      reducer = TeamDetailsReducer,
      actor = actor,
    )
}
