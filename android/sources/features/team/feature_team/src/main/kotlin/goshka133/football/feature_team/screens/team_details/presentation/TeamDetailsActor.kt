package goshka133.football.feature_team.screens.team_details.presentation

import goshka133.football.feature_team.screens.team_details.presentation.TeamDetailsCommand as Command
import goshka133.football.feature_team.screens.team_details.presentation.TeamDetailsEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import vivid.money.elmslie.coroutines.Actor

internal class TeamDetailsActor @Inject constructor() : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> = emptyFlow()
}
