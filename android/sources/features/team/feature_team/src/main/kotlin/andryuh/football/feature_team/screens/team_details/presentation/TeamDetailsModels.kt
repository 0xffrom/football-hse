package andryuh.football.feature_team.screens.team_details.presentation

import andryuh.football.domain_team.dto.Team

internal data class TeamDetailsState(
  val team: Team,
  val isLoading: Boolean = false,
)

internal sealed interface TeamDetailsEvent {

  sealed interface Ui : TeamDetailsEvent {
    sealed interface System : Ui {
      object Start : System
    }

    sealed interface Click : Ui {

      object Back : Ui
      object Delete : Ui
    }
  }

  sealed interface Internal : TeamDetailsEvent {

    object DeleteTeamSuccess : Internal
    data class DeleteTeamError(val error: Throwable) : Internal
  }
}

internal sealed interface TeamDetailsCommand {

  data class DeleteTeam(val teamId: String) : TeamDetailsCommand
}

internal sealed interface TeamDetailsEffect {

  data class ShowError(val error: Throwable) : TeamDetailsEffect
  object Close : TeamDetailsEffect
}
