package goshka133.football.feature_profile.screens.profile.presentation

import androidx.compose.runtime.Immutable
import goshka133.football.core_models.TeamApplication
import goshka133.football.core_models.mock
import goshka133.football.domain_profile.dto.Profile
import goshka133.football.domain_profile.dto.mock

@Immutable
internal data class ProfileState(
  val profile: Profile = Profile.mock(),
  val teamApplication: TeamApplication = TeamApplication.mock(),
)

internal sealed interface ProfileEvent {

  sealed interface Ui : ProfileEvent {
    object System {
      object Start : Ui
    }

    object Click {

      object TeamApplication : Ui
    }

    object Action {
      // your code
    }
  }

  sealed interface Internal : ProfileEvent {
    // your code
  }
}

internal sealed interface ProfileCommand {
  // your code
}

@Immutable
internal sealed interface ProfileEffect {

  data class OpenTeamRegistration(val profileFullName: String) : ProfileEffect
}
