package goshka133.football.feature_profile.screens.edit_profile.presentation

import goshka133.football.feature_profile.screens.edit_profile.presentation.EditProfileCommand as Command
import goshka133.football.feature_profile.screens.edit_profile.presentation.EditProfileEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import vivid.money.elmslie.coroutines.Actor

internal class EditProfileActor @Inject constructor() : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> = emptyFlow()
}
