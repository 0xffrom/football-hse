package andryuh.football.feature_team.screens.team_details.presentation

import andryuh.football.domain_team.TeamRepository
import andryuh.football.feature_team.screens.team_details.presentation.TeamDetailsCommand as Command
import andryuh.football.feature_team.screens.team_details.presentation.TeamDetailsEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor

internal class TeamDetailsActor
@Inject
constructor(
  private val teamRepository: TeamRepository,
) : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> =
    when (command) {
      is Command.DeleteTeam ->
        flow { emit(teamRepository.deleteTeam(command.teamId)) }
          .mapEvents({ Internal.DeleteTeamSuccess }, Internal::DeleteTeamError)
    }
}
