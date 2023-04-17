package andryuh.football.feature_team.screens.team_details.presentation

import andryuh.football.feature_team.screens.team_details.presentation.TeamDetailsCommand as Command
import andryuh.football.feature_team.screens.team_details.presentation.TeamDetailsEffect as Effect
import andryuh.football.feature_team.screens.team_details.presentation.TeamDetailsEvent as Event
import andryuh.football.feature_team.screens.team_details.presentation.TeamDetailsEvent.Internal
import andryuh.football.feature_team.screens.team_details.presentation.TeamDetailsEvent.Ui
import andryuh.football.feature_team.screens.team_details.presentation.TeamDetailsState as State
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object TeamDetailsReducer :
  ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

  override fun Result.ui(event: Ui) {
    when (event) {
      is Ui.System.Start -> Unit
      is Ui.Click.Back -> {
        effects { +Effect.Close }
      }
      is Ui.Click.Delete -> {
        state { copy(isLoading = true) }
        commands { +Command.DeleteTeam(state.team.id) }
      }
    }
  }

  override fun Result.internal(event: Internal) {
    when (event) {
      is Internal.DeleteTeamSuccess -> {
        state { copy(isLoading = false) }
        effects { +Effect.Close }
      }
      is Internal.DeleteTeamError -> {
        state { copy(isLoading = false) }
        effects { +Effect.ShowError(event.error) }
      }
    }
  }
}
