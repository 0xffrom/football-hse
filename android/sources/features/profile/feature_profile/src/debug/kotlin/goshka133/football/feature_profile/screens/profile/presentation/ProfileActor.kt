package goshka133.football.feature_profile.screens.profile.presentation

import goshka133.football.feature_profile.screens.profile.presentation.ProfileCommand as Command
import goshka133.football.feature_profile.screens.profile.presentation.ProfileEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import vivid.money.elmslie.coroutines.Actor

internal class ProfileActor @Inject constructor() : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> = emptyFlow()
}
