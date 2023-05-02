package andryuh.football.feature_chat.screens.conversation.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import andryuh.football.core_kotlin.Resource
import andryuh.football.domain_chat.dto.Conversation
import andryuh.football.domain_chat.dto.Message
import andryuh.football.feature_chat.models.SimpleMessage
import java.time.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime

internal data class ConversationState(
  val conversation: Conversation,
  val messages: Resource<List<Message>> = Resource.Loading,
  val pendingMessages: Set<SimpleMessage> = hashSetOf(),
  val messageTextFieldValue: TextFieldValue = TextFieldValue(),
) {

  val messageGroups: List<MessageGroup> = buildList {
    addAll(pendingMessages.map(MessageGroup::Temporary))

    addAll(
      messages.value
        ?.map { message ->
          if (message.senderPhoneNumber == conversation.phoneNumber) {
            MessageGroup.Common.ToUser(message)
          } else {
            MessageGroup.Common.FromUser(message)
          }
        }
        .orEmpty()
    )
  }

  @Immutable
  sealed interface MessageGroup {

    val date: LocalDateTime?

    sealed interface Common : MessageGroup {
      val message: Message

      override val date: LocalDateTime?
        get() = message.sendTime?.toJavaLocalDateTime()

      data class FromUser(override val message: Message) : Common

      data class ToUser(override val message: Message) : Common
    }

    data class Temporary(
      val message: SimpleMessage,
    ) : MessageGroup {

      override val date: LocalDateTime = message.date
    }
  }
}

internal sealed interface ConversationEvent {

  sealed interface Ui : ConversationEvent {

    sealed interface System : Ui {
      object Start : System
    }

    sealed interface Click : Ui {

      object SendMessage : Click
      object Back : Click
    }

    sealed interface Action : Ui {

      data class OnMessageTextFieldChange(val value: TextFieldValue) : Ui
    }
  }

  sealed interface Internal : ConversationEvent {

    data class ObserveMessagesSuccess(val messages: List<Message>) : Internal
    data class ObserveMessagesError(val error: Throwable) : Internal

    data class SendMessageSuccess(val messages: List<Message>, val message: SimpleMessage) :
      Internal
    data class SendMessageError(val error: Throwable, val message: SimpleMessage) : Internal
  }
}

internal sealed interface ConversationCommand {

  data class ObserveMessages(val conversation: Conversation) : ConversationCommand
  data class SendMessage(val conversation: Conversation, val message: SimpleMessage) :
    ConversationCommand
}

internal sealed interface ConversationEffect {

  object Close : ConversationEffect
  data class ShowError(val error: Throwable) : ConversationEffect
}
