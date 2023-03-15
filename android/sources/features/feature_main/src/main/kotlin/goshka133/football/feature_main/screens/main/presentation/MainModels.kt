package goshka133.football.feature_main.screens.main.presentation

internal data class MainState(
  val isRefreshing: Boolean = false,
)

internal sealed interface MainEvent {

  sealed interface Ui : MainEvent {
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

  sealed interface Internal : MainEvent {
    // your code
  }
}

internal sealed interface MainCommand {
  // your code
}

internal sealed interface MainEffect {
  // your code
}
