package andryuh.football.feature_team.screens.team_application_details.presentation

import andryuh.football.feature_team.screens.team_application_details.presentation.TeamApplicationDetailsCommand as Command
import andryuh.football.feature_team.screens.team_application_details.presentation.TeamApplicationDetailsEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import vivid.money.elmslie.coroutines.Actor

internal class TeamApplicationDetailsActor @Inject constructor() : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> = emptyFlow()
}
