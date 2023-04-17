package andryuh.football.feature_profile.screens.team_registration.presentation

import andryuh.football.domain_team.TeamRepository
import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationCommand as Command
import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor

internal class TeamRegistrationActor
@Inject
constructor(
  private val teamRepository: TeamRepository,
) : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> {
    return when (command) {
      is Command.CreateTeam ->
        flow { emit(teamRepository.createTeam(command.body)) }
          .mapEvents(
            { Internal.CreateTeamSuccess },
            Internal::CreateTeamError,
          )
      is Command.UploadPhoto ->
        flow { emit(teamRepository.uploadImage(command.imageUri)) }
          .mapEvents(
            { Internal.UploadPhotoSuccess },
            Internal::UploadPhotoError,
          )
    }
  }
}
