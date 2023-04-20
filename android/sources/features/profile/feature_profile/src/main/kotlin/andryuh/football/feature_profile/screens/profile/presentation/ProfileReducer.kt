package andryuh.football.feature_profile.screens.profile.presentation

import andryuh.football.core_kotlin.Resource
import andryuh.football.domain_profile.dto.Profile
import andryuh.football.domain_team.dto.TeamCreationApplicationStatus
import andryuh.football.feature_profile.screens.profile.presentation.ProfileCommand as Command
import andryuh.football.feature_profile.screens.profile.presentation.ProfileEffect as Effect
import andryuh.football.feature_profile.screens.profile.presentation.ProfileEvent as Event
import andryuh.football.feature_profile.screens.profile.presentation.ProfileEvent.Internal
import andryuh.football.feature_profile.screens.profile.presentation.ProfileEvent.Ui
import andryuh.football.feature_profile.screens.profile.presentation.ProfileState as State
import andryuh.football.ui_kit.error.SomethingWentWrongException
import timber.log.Timber
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object ProfileReducer :
  ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

  override fun Result.ui(event: Ui) {
    when (event) {
      is Ui.System.Start -> {
        commands {
          +Command.ObserveProfile
          +Command.ObserveTeamCreationStatus
          +Command.ObservePlayerApplications
        }
      }
      is Ui.Click.EditClick -> {
        performOnProfile { effects { +Effect.OpenEditProfile(this@performOnProfile) } }
      }
      is Ui.Click.TeamApplication -> {
        when (val application = state.teamApplication.value) {
          is TeamCreationApplicationStatus.Registered -> {
            performOnProfile { effects { +Effect.OpenTeamDetails(application.team) } }
          }
          is TeamCreationApplicationStatus.NotRegistered -> {
            performOnProfile { effects { +Effect.OpenTeamRegistration(this@performOnProfile) } }
          }
          null -> Unit
        }
      }
      is Ui.Click.PlayerApplicationCard -> {
        effects { +Effect.OpenProfileApplication(event.application) }
      }
      is Ui.Click.Leave -> {
        effects { +Effect.OpenAuth }
        commands { +Command.ClearSession }
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
      is Internal.ObserveTeamCreationStatusSuccess -> {
        state { copy(teamApplication = Resource.Data(event.status)) }
      }
      is Internal.ObserveTeamCreationStatusError -> {
        state { copy(teamApplication = Resource.Error(event.error)) }
        effects { +Effect.ShowError(SomethingWentWrongException()) }
      }
      is Internal.ObservePlayerApplicationsSuccess -> {
        state { copy(applications = Resource.Data(event.applications)) }
      }
      is Internal.ObservePlayerApplicationsError -> {
        state { copy(applications = Resource.Error(event.error)) }
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
