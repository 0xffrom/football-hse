package andryuh.football.feature_search.screens.search.presentation

import androidx.compose.ui.text.input.TextFieldValue
import andryuh.football.core_kotlin.Resource
import andryuh.football.domain_search.filters.Filter
import andryuh.football.domain_team.dto.TeamApplication

internal data class SearchState(
  val searchTextFieldValue: TextFieldValue = TextFieldValue(),
  val applications: Resource<List<TeamApplication>> = Resource.Loading,
  val filteredApplications: List<TeamApplication> = applications.value.orEmpty(),
  val filter: Filter = Filter()
)

internal sealed interface SearchEvent {

  sealed interface Ui : SearchEvent {
    object System {
      object Start : Ui
    }

    object Click {

      object Filter : Ui
      object CreateApplicationBanner : Ui

      data class TeamApplicationCard(val application: TeamApplication) : Ui
    }

    object Action {

      data class OnSearchTextFieldValueChange(val value: TextFieldValue) : Ui
    }
  }

  sealed interface Internal : SearchEvent {
    data class ObserveTeamApplicationsSuccess(val applications: List<TeamApplication>) : Internal
    data class ObserveTeamApplicationsError(val error: Throwable) : Internal

    data class ObserveFilterSuccess(val filter: Filter) : Internal
  }
}

internal sealed interface SearchCommand {

  object ObserveTeamApplications : SearchCommand
  object ObserveFilter : SearchCommand
}

internal sealed interface SearchEffect {

  object OpenSearchTeamApplication : SearchEffect
  data class ShowError(val error: Throwable) : SearchEffect
  data class OpenTeamApplicationDetails(val application: TeamApplication) : SearchEffect
  object OpenFilters : SearchEffect
}
