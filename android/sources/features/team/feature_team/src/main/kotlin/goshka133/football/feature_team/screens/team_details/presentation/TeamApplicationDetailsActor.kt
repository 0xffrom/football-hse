package goshka133.football.feature_team.screens.team_details.presentation

import goshka133.football.feature_team.screens.team_details.presentation.TeamApplicationDetailsCommand as Command
import goshka133.football.feature_team.screens.team_details.presentation.TeamApplicationDetailsEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import vivid.money.elmslie.coroutines.Actor

internal class TeamApplicationDetailsActor @Inject constructor() : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> = emptyFlow()
}
