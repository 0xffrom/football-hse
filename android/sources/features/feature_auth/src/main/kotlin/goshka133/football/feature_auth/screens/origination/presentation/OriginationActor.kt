package goshka133.football.feature_auth.screens.origination.presentation

import goshka133.football.feature_auth.screens.origination.presentation.OriginationCommand as Command
import goshka133.football.feature_auth.screens.origination.presentation.OriginationEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import vivid.money.elmslie.coroutines.Actor

internal class OriginationActor @Inject constructor() : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> = emptyFlow()
}
