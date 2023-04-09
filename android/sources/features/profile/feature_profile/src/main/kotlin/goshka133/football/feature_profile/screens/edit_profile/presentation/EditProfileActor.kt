package goshka133.football.feature_profile.screens.edit_profile.presentation

import goshka133.football.domain_profile.ProfileRepository
import goshka133.football.feature_profile.screens.edit_profile.presentation.EditProfileCommand as Command
import goshka133.football.feature_profile.screens.edit_profile.presentation.EditProfileEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor

internal class EditProfileActor
@Inject
constructor(
  private val profileRepository: ProfileRepository,
) : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> {
    return when (command) {
      is Command.UpdateProfile ->
        flow { emit(profileRepository.updateProfile(command.profile)) }
          .mapEvents({ Internal.UpdateProfileSuccess }, Internal::UpdateProfileError)
    }
  }
}
