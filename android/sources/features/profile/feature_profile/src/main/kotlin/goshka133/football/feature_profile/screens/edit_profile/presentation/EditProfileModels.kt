package goshka133.football.feature_profile.screens.edit_profile.presentation

import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import goshka133.football.domain_profile.dto.Profile
import goshka133.football.ui_kit.text_field.toTextFieldValue

@Immutable
internal data class EditProfileState(
  val profile: Profile,
  val fullNameTextFieldValue: TextFieldValue = profile.fullName.toTextFieldValue(),
  val footballExperienceTextFieldValue: TextFieldValue =
    profile.footballExperience.toTextFieldValue(),
  val tournamentsExperienceTextFieldValue: TextFieldValue =
    profile.tournamentsExperience.toTextFieldValue(),
  val contactInfoTextFieldValue: TextFieldValue = profile.contactInfo.toTextFieldValue(),
  val aboutInfoTextFieldValue: TextFieldValue = profile.about.toTextFieldValue(),
  val isLoading: Boolean = false,
  val loadedImageUri: Uri? = null,
)

internal sealed interface EditProfileEvent {

  sealed interface Ui : EditProfileEvent {
    object System {
      object Start : Ui
    }

    object Click {

      object Back : Ui
      object Avatar : Ui

      object Continue : Ui

      object PhotoPickerSheetContinue : Ui
      object PhotoPickerSheetClose : Ui
    }

    object Action {

      data class OnImageReceived(val uri: Uri?) : Ui

      data class OnFullNameTextFieldChange(val value: TextFieldValue) : Ui
      data class OnFootballExperienceTextFieldChange(val value: TextFieldValue) : Ui
      data class OnTournamentsExperienceTextFieldChange(val value: TextFieldValue) : Ui
      data class OnContactInfoTextFieldChange(val value: TextFieldValue) : Ui
      data class OnAboutInfoTextFieldChange(val value: TextFieldValue) : Ui
    }
  }

  sealed interface Internal : EditProfileEvent
}

internal sealed interface EditProfileCommand

@Immutable
internal sealed interface EditProfileEffect {
  object Close : EditProfileEffect
  object OpenPhotoPicker : EditProfileEffect

  object ShowBottomPhotoPickerSheet : EditProfileEffect
  object HideBottomPhotoPickerSheet : EditProfileEffect
}
