package andryuh.football.feature_chat.screens.chat.presentation

import andryuh.football.domain_chat.ChatRepository
import andryuh.football.feature_chat.screens.chat.presentation.ChatCommand as Command
import andryuh.football.feature_chat.screens.chat.presentation.ChatEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor

internal class ChatActor
@Inject
constructor(
  private val repository: ChatRepository,
) : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> {
    return when (command) {
      is Command.ObserveConversations ->
        repository
          .observeConversations()
          .mapEvents(
            Internal::ObserveConversationsSuccess,
            Internal::ObserveConversationsError,
          )
      is Command.UpdateConversations ->
        flow { emit(repository.forceUpdateConversations()) }.catch { /* empty */}.mapEvents()
    }
  }
}
