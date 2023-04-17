package andryuh.football.feature_profile.screens.team_registration.presentation

import andryuh.football.domain_team.dto.CreateTeamBody
import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationCommand as Command
import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEffect as Effect
import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEvent as Event
import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEvent.Internal
import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEvent.Ui
import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationState as State
import andryuh.football.ui_kit.error.SomethingWentWrongException
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object TeamRegistrationReducer :
  ScreenDslReducer<Event, Ui, Internal, State, Effect, Command>(Ui::class, Internal::class) {

  override fun Result.ui(event: Ui) {
    when (event) {
      is Ui.System.Start -> Unit
      is Ui.Click.Back -> {
        effects { +Effect.Close }
      }
      is Ui.Click.Avatar -> {
        effects { +Effect.ShowBottomPhotoPickerSheet }
      }
      is Ui.Action.OnImageReceived -> {
        if (event.uri != null && state.photoUri != event.uri) {
          state { copy(photoUri = event.uri) }

          effects { +Effect.HideBottomPhotoPickerSheet }
        }
      }
      is Ui.Action.OnTeamInfoTextFieldChange -> {
        state { copy(teamInfoTextField = event.value) }
      }
      is Ui.Action.OnCaptainNameTextFieldChange -> {
        state { copy(captainNameTextField = event.value) }
      }
      is Ui.Action.OnTeamNameTextFieldChange -> {
        state { copy(teamNameTextField = event.value) }
      }
      is Ui.Click.Continue -> {
        state { copy(isLoading = true) }
        commands {
          +Command.CreateTeam(
            body =
              CreateTeamBody(
                name = state.teamNameTextField.text,
                captainName = state.captainNameTextField.text,
                aboutInfo = state.teamInfoTextField.text,
              )
          )
        }
      }
      is Ui.Click.PhotoPickerSheetClose -> {
        effects { +Effect.HideBottomPhotoPickerSheet }
      }
      is Ui.Click.PhotoPickerSheetContinue -> {
        effects { +Effect.OpenPhotoPicker }
      }
    }
  }

  override fun Result.internal(event: Internal) {
    when (event) {
      is Internal.CreateTeamSuccess -> {
        val photoUri = state.photoUri
        if (photoUri != null) {
          commands { +Command.UploadPhoto(photoUri) }
        } else {
          state { copy(isLoading = false) }
          effects { +Effect.Close }
        }
      }
      is Internal.CreateTeamError -> {
        state { copy(isLoading = false) }
        effects { +Effect.ShowError(SomethingWentWrongException()) }
      }
      is Internal.UploadPhotoSuccess -> {
        state { copy(isLoading = false) }
        effects { +Effect.Close }
      }
      is Internal.UploadPhotoError -> {
        state { copy(isLoading = false) }
        effects { +Effect.ShowError(SomethingWentWrongException()) }
      }
    }
  }
}
