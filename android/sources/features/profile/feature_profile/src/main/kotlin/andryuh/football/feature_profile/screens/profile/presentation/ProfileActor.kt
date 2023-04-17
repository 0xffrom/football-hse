package andryuh.football.feature_profile.screens.profile.presentation

import andryuh.football.domain_profile.ProfileRepository
import andryuh.football.domain_team.TeamRepository
import andryuh.football.feature_profile.screens.profile.presentation.ProfileCommand as Command
import andryuh.football.feature_profile.screens.profile.presentation.ProfileEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import vivid.money.elmslie.coroutines.Actor

internal class ProfileActor
@Inject
constructor(
  private val profileRepository: ProfileRepository,
  private val teamRepository: TeamRepository,
) : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> {
    return when (command) {
      is Command.ObserveProfile ->
        profileRepository
          .observeProfile()
          .mapEvents(
            Internal::ObserveProfileSuccess,
            Internal::ObserveProfileError,
          )
      is Command.ObserveTeamCreationStatus ->
        teamRepository
          .observeTeamCreationApplication()
          .mapEvents(
            Internal::ObserveTeamCreationStatusSuccess,
            Internal::ObserveTeamCreationStatusError,
          )
    }
  }
}
