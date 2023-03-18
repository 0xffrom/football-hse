package goshka133.football.feature_main.screens.main.presentation

import goshka133.football.feature_main.screens.main.models.BottomBarTabType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

internal data class MainState(
  val selectedTab: BottomBarTabType,
  val tabs: ImmutableList<BottomBarTabType> = BottomBarTabType.values().toList().toImmutableList(),
)

internal sealed interface MainEvent {

  sealed interface Ui : MainEvent {
    object System {
      object Start : Ui
    }

    object Click {

      object Back: Ui
      data class BottomBarTab(val tab: BottomBarTabType): Ui
    }

    object Action {
      // your code
    }
  }

  sealed interface Internal : MainEvent {
    // your code
  }
}

internal sealed interface MainCommand {
  // your code
}

internal sealed interface MainEffect {

  object Close : MainEffect
  data class ShowError(val error: Throwable) : MainEffect
}
