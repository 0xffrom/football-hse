package goshka133.football.feature_main.screens.main.presentation

import goshka133.football.feature_main.screens.main.presentation.MainCommand as Command
import goshka133.football.feature_main.screens.main.presentation.MainEffect as Effect
import goshka133.football.feature_main.screens.main.presentation.MainEvent as Event
import goshka133.football.feature_main.screens.main.presentation.MainEvent.Internal
import goshka133.football.feature_main.screens.main.presentation.MainEvent.Ui
import goshka133.football.feature_main.screens.main.presentation.MainState as State
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object MainReducer :
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
