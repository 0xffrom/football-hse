package goshka133.football.feature_team.screens.team_details.presentation

import goshka133.football.feature_team.screens.team_details.presentation.TeamApplicationDetailsCommand as Command
import goshka133.football.feature_team.screens.team_details.presentation.TeamApplicationDetailsEffect as Effect
import goshka133.football.feature_team.screens.team_details.presentation.TeamApplicationDetailsEvent as Event
import goshka133.football.feature_team.screens.team_details.presentation.TeamApplicationDetailsEvent.Internal
import goshka133.football.feature_team.screens.team_details.presentation.TeamApplicationDetailsEvent.Ui
import goshka133.football.feature_team.screens.team_details.presentation.TeamApplicationDetailsState as State
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object TeamApplicationDetailsReducer :
  ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

  override fun Result.ui(event: Ui) {
    when (event) {
      is Ui.System.Start -> {
      }
      is Ui.Click.Back -> {
        effects {
          +Effect.Close
        }
      }
    }
  }

  override fun Result.internal(event: Internal) = Unit
}
