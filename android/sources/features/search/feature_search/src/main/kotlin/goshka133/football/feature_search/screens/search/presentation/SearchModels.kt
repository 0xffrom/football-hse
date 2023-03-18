package goshka133.football.feature_search.screens.search.presentation

internal data class SearchState(
  val isRefreshing: Boolean = false,
)

internal sealed interface SearchEvent {

  sealed interface Ui : SearchEvent {
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

  sealed interface Internal : SearchEvent {
    // your code
  }
}

internal sealed interface SearchCommand {
  // your code
}

internal sealed interface SearchEffect {
  // your code
}
