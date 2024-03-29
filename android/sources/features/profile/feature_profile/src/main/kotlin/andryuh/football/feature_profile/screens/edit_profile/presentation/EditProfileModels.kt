package andryuh.football.feature_profile.screens.edit_profile.presentation

import android.net.Uri
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import andryuh.football.domain_profile.dto.Profile
import andryuh.football.ui_kit.text_field.toTextFieldValue
import java.io.File

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

  sealed interface Internal : EditProfileEvent {

    object UpdateProfileSuccess : Internal
    data class UpdateProfileError(val error: Throwable) : Internal

    object UploadPhotoSuccess : Internal
    data class UploadPhotoError(val error: Throwable) : Internal
  }
}

internal sealed interface EditProfileCommand {

  data class UpdateProfile(
    val profile: Profile,
  ) : EditProfileCommand

  data class UploadPhoto(val photo: Uri) : EditProfileCommand
}

@Immutable
internal sealed interface EditProfileEffect {
  object Close : EditProfileEffect
  object OpenPhotoPicker : EditProfileEffect

  object ShowBottomPhotoPickerSheet : EditProfileEffect
  object HideBottomPhotoPickerSheet : EditProfileEffect

  data class ShowError(val error: Throwable) : EditProfileEffect
}
