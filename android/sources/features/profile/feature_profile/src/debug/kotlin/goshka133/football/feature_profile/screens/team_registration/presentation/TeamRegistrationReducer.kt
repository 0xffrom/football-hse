package goshka133.football.feature_profile.screens.team_registration.presentation

import goshka133.football.feature_profile.screens.team_registration.presentation.TeamRegistrationCommand as Command
import goshka133.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEffect as Effect
import goshka133.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEvent as Event
import goshka133.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEvent.Internal
import goshka133.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEvent.Ui
import goshka133.football.feature_profile.screens.team_registration.presentation.TeamRegistrationState as State
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object TeamRegistrationReducer :
  ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

  override fun Result.ui(event: Ui) {
    when (event) {
      is Ui.System.Start -> Unit
      is Ui.Click.Back -> {
        effects { +Effect.Close }
      }
    }
  }

  override fun Result.internal(event: Internal) = Unit
}
