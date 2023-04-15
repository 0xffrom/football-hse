package andryuh.football.feature_profile.screens.team_registration.presentation

import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationCommand as Command
import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import vivid.money.elmslie.coroutines.Actor

internal class TeamRegistrationActor @Inject constructor() : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> = emptyFlow()
}
