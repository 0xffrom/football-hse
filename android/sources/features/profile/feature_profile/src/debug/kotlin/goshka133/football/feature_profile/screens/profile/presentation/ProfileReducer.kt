package goshka133.football.feature_profile.screens.profile.presentation

import goshka133.football.domain_team.TeamCreationApplicationStatus
import goshka133.football.feature_profile.screens.profile.presentation.ProfileCommand as Command
import goshka133.football.feature_profile.screens.profile.presentation.ProfileEffect as Effect
import goshka133.football.feature_profile.screens.profile.presentation.ProfileEvent as Event
import goshka133.football.feature_profile.screens.profile.presentation.ProfileEvent.Internal
import goshka133.football.feature_profile.screens.profile.presentation.ProfileEvent.Ui
import goshka133.football.feature_profile.screens.profile.presentation.ProfileState as State
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object ProfileReducer :
  ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

  override fun Result.ui(event: Ui) {
    when (event) {
      is Ui.System.Start -> {
        // your code
      }
      is Ui.Click.EditClick -> {
        effects { +Effect.OpenEditProfile(state.profile) }
      }
      is Ui.Click.TeamApplication -> {
        when (state.teamApplication) {
          is TeamCreationApplicationStatus.Registered -> {
            // TODO
          }
          is TeamCreationApplicationStatus.NotRegistered -> {
            effects { +Effect.OpenTeamRegistration(state.profile) }
          }
        }
      }
    }
  }

  override fun Result.internal(event: Internal) = Unit
}
