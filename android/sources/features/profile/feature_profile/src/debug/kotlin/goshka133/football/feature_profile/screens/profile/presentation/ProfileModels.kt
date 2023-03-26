package goshka133.football.feature_profile.screens.profile.presentation

import androidx.compose.runtime.Immutable
import goshka133.football.domain_profile.dto.Profile
import goshka133.football.domain_profile.dto.mock
import goshka133.football.domain_team.TeamCreationApplicationStatus
import goshka133.football.domain_team.mock

@Immutable
internal data class ProfileState(
  val profile: Profile = Profile.mock(),
  val teamApplication: TeamCreationApplicationStatus = TeamCreationApplicationStatus.mock(),
)

internal sealed interface ProfileEvent {

  sealed interface Ui : ProfileEvent {
    object System {
      object Start : Ui
    }

    object Click {

      object TeamApplication : Ui
      object EditClick : Ui
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

  data class OpenTeamRegistration(val profile: Profile) : ProfileEffect
  data class OpenEditProfile(val profile: Profile) : ProfileEffect
}
