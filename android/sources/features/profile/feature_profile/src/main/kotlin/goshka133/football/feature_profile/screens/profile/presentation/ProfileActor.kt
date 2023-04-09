package goshka133.football.feature_profile.screens.profile.presentation

import goshka133.football.domain_profile.ProfileRepository
import goshka133.football.feature_profile.screens.profile.presentation.ProfileCommand as Command
import goshka133.football.feature_profile.screens.profile.presentation.ProfileEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import vivid.money.elmslie.coroutines.Actor

internal class ProfileActor
@Inject
constructor(
  private val profileRepository: ProfileRepository,
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
    }
  }
}
