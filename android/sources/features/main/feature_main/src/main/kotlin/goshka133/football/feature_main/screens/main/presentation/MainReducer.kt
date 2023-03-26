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
      is Ui.Click.Back -> {
        if (state.tabsHistory.size > 1) {
          val tab = state.tabsHistory[state.tabsHistory.size - 2]

          state {
            copy(
              selectedTab = tab,
              previousTab = state.selectedTab,
              tabsHistory = state.tabsHistory.dropLast(1),
            )
          }
        }
      }
      is Ui.Click.BottomBarTab -> {
        if (state.selectedTab == event.tab) return

        state {
          copy(
            selectedTab = event.tab,
            previousTab = state.selectedTab,
            tabsHistory = state.tabsHistory + event.tab,
          )
        }
      }
    }
  }

  override fun Result.internal(event: Internal) = Unit
}
