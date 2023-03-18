package goshka133.football.feature_main.screens.main.presentation

import goshka133.football.feature_main.screens.main.presentation.MainCommand as Command
import goshka133.football.feature_main.screens.main.presentation.MainEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import vivid.money.elmslie.coroutines.Actor

internal class MainActor @Inject constructor() : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> = emptyFlow()
}
