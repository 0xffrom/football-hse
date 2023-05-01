package andryuh.football.feature_chat.screens.chat.presentation

import andryuh.football.core_kotlin.Resource
import andryuh.football.domain_chat.dto.Conversation

internal data class ChatState(
  val conversations: Resource<List<Conversation>> = Resource.Loading,
)

internal sealed interface ChatEvent {

  sealed interface Ui : ChatEvent {
    object System {
      object Start : Ui
    }

    object Click {

      data class ConversationCard(val conversation: Conversation) : Ui
    }
  }

  sealed interface Internal : ChatEvent {

    data class ObserveConversationsSuccess(val conversations: List<Conversation>) : Internal
    data class ObserveConversationsError(val error: Throwable) : Internal
  }
}

internal sealed interface ChatCommand {

  object ObserveConversations : ChatCommand
  object UpdateConversations: ChatCommand
}

internal sealed interface ChatEffect {

  data class OpenConversation(val conversation: Conversation) : ChatEffect
}
