package goshka133.football.feature_chat.screens.chat.presentation

import goshka133.football.feature_chat.screens.chat.presentation.ChatCommand as Command
import goshka133.football.feature_chat.screens.chat.presentation.ChatEffect as Effect
import goshka133.football.feature_chat.screens.chat.presentation.ChatEvent as Event
import goshka133.football.feature_chat.screens.chat.presentation.ChatEvent.Internal
import goshka133.football.feature_chat.screens.chat.presentation.ChatEvent.Ui
import goshka133.football.feature_chat.screens.chat.presentation.ChatState as State
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
