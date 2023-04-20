package andryuh.football.feature_search.screens.search_player.presentation

import androidx.compose.ui.text.input.TextFieldValue
import andryuh.football.core_kotlin.Resource
import andryuh.football.domain_profile.dto.PlayerApplication
import andryuh.football.domain_search.filters.Filter

internal data class SearchPlayerState(
  val searchTextFieldValue: TextFieldValue = TextFieldValue(),
  val applications: Resource<List<PlayerApplication>> = Resource.Loading,
  val filteredApplications: List<PlayerApplication> = applications.value.orEmpty(),
  val filter: Filter = Filter()
)

internal sealed interface SearchPlayerEvent {

  sealed interface Ui : SearchPlayerEvent {
    object System {
      object Start : Ui
    }

    object Click {

      object Filter : Ui
      object CreateSearchPlayerApplicationBanner : Ui

      data class PlayerApplicationCard(val application: PlayerApplication) : Ui
    }

    object Action {

      data class OnSearchTextFieldValueChange(val value: TextFieldValue) : Ui
    }
  }

  sealed interface Internal : SearchPlayerEvent {
    data class ObservePlayerApplicationsSuccess(val applications: List<PlayerApplication>) :
      Internal
    data class ObservePlayerApplicationsError(val error: Throwable) : Internal

    data class ObserveFilterSuccess(val filter: Filter) : Internal
  }
}

internal sealed interface SearchPlayerCommand {

  object ObservePlayerApplications : SearchPlayerCommand
  object ObserveFilter : SearchPlayerCommand
}

internal sealed interface SearchPlayerEffect {

  object OpenSearchPlayerApplication : SearchPlayerEffect
  data class ShowError(val error: Throwable) : SearchPlayerEffect
  data class OpenPlayerApplicationDetails(val application: PlayerApplication) : SearchPlayerEffect
  object OpenFilters : SearchPlayerEffect
}
