package goshka133.football.feature_team.screens.team_details.presentation

import goshka133.football.feature_team.screens.team_details.presentation.TeamDetailsCommand as Command
import goshka133.football.feature_team.screens.team_details.presentation.TeamDetailsEffect as Effect
import goshka133.football.feature_team.screens.team_details.presentation.TeamDetailsEvent as Event
import goshka133.football.feature_team.screens.team_details.presentation.TeamDetailsEvent.Internal
import goshka133.football.feature_team.screens.team_details.presentation.TeamDetailsEvent.Ui
import goshka133.football.feature_team.screens.team_details.presentation.TeamDetailsState as State
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object TeamDetailsReducer :
  ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

  override fun Result.ui(event: Ui) {
    when (event) {
      is Ui.System.Start -> {
        // your code
      }
    }
  }

  override fun Result.internal(event: Internal) = Unit
}
