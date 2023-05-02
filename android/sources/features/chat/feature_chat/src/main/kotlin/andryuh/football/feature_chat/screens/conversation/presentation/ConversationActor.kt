package andryuh.football.feature_chat.screens.conversation.presentation

import andryuh.football.domain_chat.ChatRepository
import andryuh.football.feature_chat.screens.conversation.presentation.ConversationCommand as Command
import andryuh.football.feature_chat.screens.conversation.presentation.ConversationEvent.Internal
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.coroutines.Actor

internal class ConversationActor
@Inject
constructor(
  private val chatRepository: ChatRepository,
) : Actor<Command, Internal> {

  override fun execute(command: Command): Flow<Internal> =
    when (command) {
      is Command.SendMessage ->
        flow {
            emit(
              chatRepository.sendMessage(
                conversation = command.conversation,
                messageContent = command.message.content,
              )
            )
          }
          .mapEvents(
            eventMapper = { messages -> Internal.SendMessageSuccess(messages, command.message) },
            errorMapper = { error -> Internal.SendMessageError(error, command.message) },
          )
      is Command.ObserveMessages ->
        chatRepository
          .observeMessages(command.conversation)
          .mapEvents(Internal::ObserveMessagesSuccess, Internal::ObserveMessagesError)
    }
}
