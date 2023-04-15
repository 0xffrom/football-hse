package andryuh.football.feature_chat.screens.chat.presentation

import andryuh.football.feature_chat.screens.chat.presentation.ChatCommand as Command
import andryuh.football.feature_chat.screens.chat.presentation.ChatEffect as Effect
import andryuh.football.feature_chat.screens.chat.presentation.ChatEvent as Event
import andryuh.football.feature_chat.screens.chat.presentation.ChatEvent.Internal
import andryuh.football.feature_chat.screens.chat.presentation.ChatEvent.Ui
import andryuh.football.feature_chat.screens.chat.presentation.ChatState as State
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object ChatReducer :
  ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

  override fun Result.ui(event: Ui) {
    when (event) {
      is Ui.System.Start -> {
        // your code
      }
    }
  }

  override fun Result.internal(event: Internal) = Unit
}
