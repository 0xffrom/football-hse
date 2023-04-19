package andryuh.football.feature_search.screens.search.presentation

import andryuh.football.core_kotlin.Resource
import andryuh.football.feature_search.screens.search.presentation.SearchCommand as Command
import andryuh.football.feature_search.screens.search.presentation.SearchEffect as Effect
import andryuh.football.feature_search.screens.search.presentation.SearchEvent as Event
import andryuh.football.feature_search.screens.search.presentation.SearchEvent.Internal
import andryuh.football.feature_search.screens.search.presentation.SearchEvent.Ui
import andryuh.football.feature_search.screens.search.presentation.SearchState as State
import andryuh.football.ui_kit.error.SomethingWentWrongException
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object SearchReducer :
  ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

  override fun Result.ui(event: Ui) {
    when (event) {
      is Ui.System.Start -> {
        commands { +Command.ObserveTeamApplications }
      }
      is Ui.Action.OnSearchTextFieldValueChange -> {
        state { copy(searchTextFieldValue = event.value) }
        applySearchFilter()
      }
      is Ui.Click.Filter -> {
        // TODO: Implement this event.
      }
      is Ui.Click.CreateApplicationBanner -> {
        effects { +Effect.OpenSearchTeamApplication }
      }
      is Ui.Click.TeamApplicationCard -> {
        effects { +Effect.OpenTeamApplicationDetails(event.application) }
      }
    }
  }

  override fun Result.internal(event: Internal) {
    when (event) {
      is Internal.ObserveTeamApplicationsSuccess -> {
        state {
          copy(
            applications = Resource.Data(event.applications),
          )
        }
        applySearchFilter()
      }
      is Internal.ObserveTeamApplicationsError -> {
        if (state.applications !is Resource.Loading) {
          state { copy(applications = Resource.Error(event.error)) }
        } else {
          effects { +Effect.ShowError(SomethingWentWrongException()) }
        }
      }
    }
  }

  private fun Result.applySearchFilter() {
    state {
      copy(
        filteredApplications =
          if (state.searchTextFieldValue.text.isNotBlank()) {
            state.applications.value.orEmpty().filter { teamApplication ->
              teamApplication.name.lowercase().contains(state.searchTextFieldValue.text.lowercase())
            }
          } else state.applications.value.orEmpty(),
      )
    }
  }
}
