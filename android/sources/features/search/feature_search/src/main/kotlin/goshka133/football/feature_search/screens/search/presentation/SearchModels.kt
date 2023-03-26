package goshka133.football.feature_search.screens.search.presentation

import androidx.compose.ui.text.input.TextFieldValue
import goshka133.football.domain_team.TeamApplication
import goshka133.football.domain_team.mockList

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
  // your code
}
