package andryuh.football.feature_team.screens.team_details.presentation

import andryuh.football.domain_team.dto.TeamApplication

internal data class TeamApplicationDetailsState(
    val teamApplication: TeamApplication,
)

internal sealed interface TeamApplicationDetailsEvent {

  sealed interface Ui : TeamApplicationDetailsEvent {
    object System {
      object Start : Ui
    }

    object Click {
      object Back : Ui
    }

    object Action {
      // your code
    }
  }

  sealed interface Internal : TeamApplicationDetailsEvent {
    // your code
  }
}

internal sealed interface TeamApplicationDetailsCommand {
  // your code
}

internal sealed interface TeamApplicationDetailsEffect {

  object Close : TeamApplicationDetailsEffect
}
