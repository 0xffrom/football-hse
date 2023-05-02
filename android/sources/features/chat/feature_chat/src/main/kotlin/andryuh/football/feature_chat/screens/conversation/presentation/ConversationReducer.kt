package andryuh.football.feature_chat.screens.conversation.presentation

import androidx.compose.ui.text.input.TextFieldValue
import andryuh.football.core_kotlin.Resource
import andryuh.football.feature_chat.models.SimpleMessage
import andryuh.football.feature_chat.screens.conversation.presentation.ConversationCommand as Command
import andryuh.football.feature_chat.screens.conversation.presentation.ConversationEffect as Effect
import andryuh.football.feature_chat.screens.conversation.presentation.ConversationEvent as Event
import andryuh.football.feature_chat.screens.conversation.presentation.ConversationEvent.Internal
import andryuh.football.feature_chat.screens.conversation.presentation.ConversationEvent.Ui
import andryuh.football.feature_chat.screens.conversation.presentation.ConversationState as State
import java.time.LocalDateTime
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object ConversationReducer :
  ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

  override fun Result.ui(event: Ui) {
    when (event) {
      is Ui.System.Start -> {
        commands { +Command.ObserveMessages(state.conversation) }
      }
      is Ui.Action.OnMessageTextFieldChange -> {
        state {
          copy(
            messageTextFieldValue = event.value,
          )
        }
      }
      is Ui.Click.SendMessage -> {
        val messageContent = state.messageTextFieldValue.text

        if (messageContent.isBlank()) return

        val message =
          SimpleMessage(
            content = messageContent,
            date = LocalDateTime.now(),
          )

        state {
          copy(
            pendingMessages =
              buildSet {
                addAll(state.pendingMessages)
                add(message)
              },
            messageTextFieldValue = TextFieldValue(),
          )
        }

        commands {
          +Command.SendMessage(
            conversation = state.conversation,
            message = message,
          )
        }
      }
      is Ui.Click.Back -> {
        effects { +Effect.Close }
      }
    }
  }

  override fun Result.internal(event: Internal) {
    when (event) {
      is Internal.ObserveMessagesSuccess -> {
        state { copy(messages = Resource.Data(event.messages)) }
      }
      is Internal.ObserveMessagesError -> Unit
      is Internal.SendMessageSuccess -> {
        state {
          copy(
            messages = Resource.Data(event.messages),
            pendingMessages = pendingMessages - event.message,
          )
        }
      }
      is Internal.SendMessageError -> {
        state {
          copy(
            pendingMessages = pendingMessages - event.message,
          )
        }
        effects { +Effect.ShowError(event.error) }
      }
    }
  }
}
