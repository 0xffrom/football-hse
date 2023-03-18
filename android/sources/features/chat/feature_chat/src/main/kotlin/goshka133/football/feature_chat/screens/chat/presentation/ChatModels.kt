package goshka133.football.feature_chat.screens.chat.presentation

internal data class ChatState(
  val isRefreshing: Boolean = false,
)

internal sealed interface ChatEvent {

  sealed interface Ui : ChatEvent {
    object System {
      object Start : Ui
    }

    object Click {
      // your code
    }

    object Action {
      // your code
    }
  }

  sealed interface Internal : ChatEvent {
    // your code
  }
}

internal sealed interface ChatCommand {
  // your code
}

internal sealed interface ChatEffect {
  // your code
}
