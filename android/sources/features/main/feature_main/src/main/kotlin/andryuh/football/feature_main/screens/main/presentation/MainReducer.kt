package andryuh.football.feature_main.screens.main.presentation

import andryuh.football.feature_main.screens.main.models.BottomBarTabType
import andryuh.football.feature_main.screens.main.presentation.MainCommand as Command
import andryuh.football.feature_main.screens.main.presentation.MainEffect as Effect
import andryuh.football.feature_main.screens.main.presentation.MainEvent as Event
import andryuh.football.feature_main.screens.main.presentation.MainEvent.Internal
import andryuh.football.feature_main.screens.main.presentation.MainEvent.Ui
import andryuh.football.feature_main.screens.main.presentation.MainState as State
import kotlinx.collections.immutable.toImmutableList
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object MainReducer :
  ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

  override fun Result.ui(event: Ui) {
    when (event) {
      is Ui.System.Start -> {
        commands { +Command.ObserveCaptain }
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

  override fun Result.internal(event: Internal) {
    when (event) {
      is Internal.ObserveCaptainSuccess -> {
        state {
          copy(
            isCaptain = event.isCaptain,
            tabs =
              BottomBarTabType.values()
                .run {
                  if (event.isCaptain) {
                    toList()
                  } else {
                    filter { it != BottomBarTabType.SearchPlayer }
                  }
                }
                .toImmutableList(),
          )
        }
      }
    }
  }
}
