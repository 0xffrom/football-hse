package goshka133.football.feature_profile.screens.edit_profile.presentation

import goshka133.football.feature_profile.screens.edit_profile.presentation.EditProfileCommand as Command
import goshka133.football.feature_profile.screens.edit_profile.presentation.EditProfileEffect as Effect
import goshka133.football.feature_profile.screens.edit_profile.presentation.EditProfileEvent as Event
import goshka133.football.feature_profile.screens.edit_profile.presentation.EditProfileEvent.Internal
import goshka133.football.feature_profile.screens.edit_profile.presentation.EditProfileEvent.Ui
import goshka133.football.feature_profile.screens.edit_profile.presentation.EditProfileState as State
import goshka133.football.ui_kit.error.SomethingWentWrongException
import vivid.money.elmslie.core.store.dsl_reducer.ScreenDslReducer

internal object EditProfileReducer :
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
        if (event.uri != null && state.loadedImageUri != event.uri) {
          state { copy(loadedImageUri = event.uri) }

          effects { +Effect.HideBottomPhotoPickerSheet }
        }
      }
      is Ui.Click.Continue -> {
        if (state.isLoading) return

        state { copy(isLoading = true) }
        commands {
          +Command.UpdateProfile(
            profile =
              state.profile.copy(
                fullName = state.fullNameTextFieldValue.text,
                footballExperience = state.footballExperienceTextFieldValue.text,
                tournamentsExperience = state.tournamentsExperienceTextFieldValue.text,
                contactInfo = state.contactInfoTextFieldValue.text,
                about = state.aboutInfoTextFieldValue.text,
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
      is Ui.Action.OnFullNameTextFieldChange -> {
        state { copy(fullNameTextFieldValue = event.value) }
      }
      is Ui.Action.OnFootballExperienceTextFieldChange -> {
        state { copy(footballExperienceTextFieldValue = event.value) }
      }
      is Ui.Action.OnTournamentsExperienceTextFieldChange -> {
        state { copy(tournamentsExperienceTextFieldValue = event.value) }
      }
      is Ui.Action.OnAboutInfoTextFieldChange -> {
        state { copy(aboutInfoTextFieldValue = event.value) }
      }
      is Ui.Action.OnContactInfoTextFieldChange -> {
        state { copy(contactInfoTextFieldValue = event.value) }
      }
    }
  }

  override fun Result.internal(event: Internal) {
    when (event) {
      is Internal.UpdateProfileError -> {
        state { copy(isLoading = false) }
        effects { +Effect.ShowError(SomethingWentWrongException()) }
      }
      is Internal.UpdateProfileSuccess -> {
        state { copy(isLoading = false) }
        effects { +Effect.Close }
      }
    }
  }
}
