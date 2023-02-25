package goshka133.football.feature_auth.screens.auth.presentation

import goshka133.football.feature_auth.screens.auth.presentation.AuthEvent.Internal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject
import goshka133.football.feature_auth.screens.auth.presentation.AuthCommand as Command

internal class AuthActor @Inject constructor() : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> = emptyFlow()
}
