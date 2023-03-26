package goshka133.football.feature_team.screens.team_details.presentation

internal data class TeamDetailsState(
  val isRefreshing: Boolean = false,
)

internal sealed interface TeamDetailsEvent {

  sealed interface Ui : TeamDetailsEvent {
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

  sealed interface Internal : TeamDetailsEvent {
    // your code
  }
}

internal sealed interface TeamDetailsCommand {
  // your code
}

internal sealed interface TeamDetailsEffect {
  // your code
}
