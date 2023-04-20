package andryuh.football.feature_profile.screens.profile_application.presentation

import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationCommand as Command
import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import vivid.money.elmslie.coroutines.Actor

internal class ProfileApplicationActor @Inject constructor() : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> = emptyFlow()
}
