package andryuh.football.feature_chat.screens.chat.presentation

import andryuh.football.feature_chat.screens.chat.presentation.ChatCommand as Command
import andryuh.football.feature_chat.screens.chat.presentation.ChatEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import vivid.money.elmslie.coroutines.Actor

internal class ChatActor @Inject constructor() : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> = emptyFlow()
}
