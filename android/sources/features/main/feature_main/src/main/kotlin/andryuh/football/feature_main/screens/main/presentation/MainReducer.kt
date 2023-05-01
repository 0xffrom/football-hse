package andryuh.football.feature_main.screens.main.presentation

import andryuh.football.feature_main.screens.main.models.BottomBarTabType
import andryuh.football.feature_main.screens.main.models.toCommonTab
import andryuh.football.feature_main.screens.main.models.toTab
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
        commands {
          +Command.ObserveCaptain
          +Command.ObserveTab
        }
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

          commands { +Command.ChangeTab(tab.toCommonTab()) }
        }
      }
      is Ui.Click.BottomBarTab -> {
        commands { +Command.ChangeTab(event.tab.toCommonTab()) }
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
      is Internal.ObserveTabSuccess -> {
        val tab = event.tab.toTab()

        state {
          copy(
            selectedTab = tab,
            previousTab = state.selectedTab,
            tabsHistory = state.tabsHistory + tab,
          )
        }
      }
    }
  }
}
