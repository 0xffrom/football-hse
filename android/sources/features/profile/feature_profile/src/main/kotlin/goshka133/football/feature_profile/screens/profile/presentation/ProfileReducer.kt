package goshka133.football.feature_profile.screens.profile.presentation

import goshka133.football.core_kotlin.Resource
import goshka133.football.domain_profile.dto.Profile
import goshka133.football.domain_team.TeamCreationApplicationStatus
import goshka133.football.feature_profile.screens.profile.presentation.ProfileCommand as Command
import goshka133.football.feature_profile.screens.profile.presentation.ProfileEffect as Effect
import goshka133.football.feature_profile.screens.profile.presentation.ProfileEvent as Event
import goshka133.football.feature_profile.screens.profile.presentation.ProfileEvent.Internal
import goshka133.football.feature_profile.screens.profile.presentation.ProfileEvent.Ui
import goshka133.football.feature_profile.screens.profile.presentation.ProfileState as State
import goshka133.football.ui_kit.error.SomethingWentWrongException
import timber.log.Timber
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object ProfileReducer :
  ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

  override fun Result.ui(event: Ui) {
    when (event) {
      is Ui.System.Start -> {
        commands { +Command.ObserveProfile }
      }
      is Ui.Click.EditClick -> {
        performOnProfile { effects { +Effect.OpenEditProfile(this@performOnProfile) } }
      }
      is Ui.Click.TeamApplication -> {
        when (state.teamApplication) {
          is TeamCreationApplicationStatus.Registered -> {
            // TODO
          }
          is TeamCreationApplicationStatus.NotRegistered -> {
            performOnProfile { effects { +Effect.OpenTeamRegistration(this@performOnProfile) } }
          }
        }
      }
    }
  }

  override fun Result.internal(event: Internal) {
    when (event) {
      is Internal.ObserveProfileSuccess -> {
        state { copy(profile = Resource.Data(event.profile)) }
      }
      is Internal.ObserveProfileError -> {
        state { copy(profile = Resource.Error(event.error)) }
        effects { +Effect.ShowError(SomethingWentWrongException()) }
      }
    }
  }

  private fun Result.performOnProfile(block: Profile.() -> Unit) {
    val profile = state.profile.value

    if (profile == null) {
      Timber.e("Incorrect state. Profile is null")
    } else {
      profile.block()
    }
  }
}
