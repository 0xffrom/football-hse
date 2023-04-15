package andryuh.football.feature_search.screens.search.presentation

import androidx.compose.ui.text.input.TextFieldValue
import andryuh.football.domain_team.TeamApplication
import andryuh.football.domain_team.mockList

internal data class SearchState(
  val searchTextFieldValue: TextFieldValue = TextFieldValue(),
  val applications: List<TeamApplication> = TeamApplication.mockList(),
  val filteredApplications: List<TeamApplication> = applications,
)

internal sealed interface SearchEvent {

  sealed interface Ui : SearchEvent {
    object System {
      object Start : Ui
    }

    object Click {

      object Filter : Ui
      object CreateApplicationBanner : Ui

      data class TeamApplicationCard(val application: TeamApplication): Ui
    }

    object Action {

      data class OnSearchTextFieldValueChange(val value: TextFieldValue) : Ui
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

  data class OpenTeamApplicationDetails(val application: TeamApplication): SearchEffect
}
