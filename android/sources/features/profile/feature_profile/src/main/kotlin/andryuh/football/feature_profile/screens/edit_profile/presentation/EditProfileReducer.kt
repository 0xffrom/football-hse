package andryuh.football.feature_profile.screens.edit_profile.presentation

import androidx.core.net.toFile
import andryuh.football.feature_profile.screens.edit_profile.presentation.EditProfileCommand as Command
import andryuh.football.feature_profile.screens.edit_profile.presentation.EditProfileEffect as Effect
import andryuh.football.feature_profile.screens.edit_profile.presentation.EditProfileEvent as Event
import andryuh.football.feature_profile.screens.edit_profile.presentation.EditProfileEvent.Internal
import andryuh.football.feature_profile.screens.edit_profile.presentation.EditProfileEvent.Ui
import andryuh.football.feature_profile.screens.edit_profile.presentation.EditProfileState as State
import andryuh.football.ui_kit.error.SomethingWentWrongException
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
      is Internal.UpdateProfileSuccess -> {
        val imageUri = state.loadedImageUri
        if (imageUri != null) {
          commands { +Command.UploadPhoto(imageUri) }
        } else {
          state { copy(isLoading = false) }
          effects { +Effect.Close }
        }
      }
      is Internal.UpdateProfileError -> {
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
