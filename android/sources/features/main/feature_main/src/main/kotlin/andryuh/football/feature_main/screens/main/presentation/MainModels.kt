package andryuh.football.feature_main.screens.main.presentation

import andryuh.football.domain_main.CommonBottomBarTabType
import andryuh.football.feature_main.screens.main.models.BottomBarTabType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

internal data class MainState(
  val selectedTab: BottomBarTabType,
  val previousTab: BottomBarTabType = selectedTab,
  val isCaptain: Boolean = false,
  val tabs: ImmutableList<BottomBarTabType> =
    BottomBarTabType.values().filter { it != BottomBarTabType.SearchPlayer }.toImmutableList(),
  val tabsHistory: List<BottomBarTabType> = listOf(selectedTab),
)

internal sealed interface MainEvent {

  sealed interface Ui : MainEvent {
    object System {
      object Start : Ui
    }

    object Click {

      object Back : Ui
      data class BottomBarTab(val tab: BottomBarTabType) : Ui
    }
  }

  sealed interface Internal : MainEvent {

    data class ObserveCaptainSuccess(val isCaptain: Boolean) : Internal

    data class ObserveTabSuccess(val tab: CommonBottomBarTabType) : Internal
  }
}

internal sealed interface MainCommand {

  object ObserveCaptain : MainCommand
  object ObserveTab : MainCommand

  data class ChangeTab(val tab: CommonBottomBarTabType) : MainCommand
}

internal sealed interface MainEffect {

  object Close : MainEffect
  data class ShowError(val error: Throwable) : MainEffect
}
