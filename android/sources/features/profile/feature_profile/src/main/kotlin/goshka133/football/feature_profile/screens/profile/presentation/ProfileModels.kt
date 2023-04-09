package goshka133.football.feature_profile.screens.profile.presentation

import androidx.compose.runtime.Immutable
import goshka133.football.core_kotlin.Resource
import goshka133.football.domain_profile.dto.Profile
import goshka133.football.domain_profile.dto.mock
import goshka133.football.domain_team.TeamCreationApplicationStatus
import goshka133.football.domain_team.mock

@Immutable
internal data class ProfileState(
  val profile: Resource<Profile> = Resource.Loading,
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

    data class ObserveProfileSuccess(val profile: Profile) : Internal
    data class ObserveProfileError(val error: Throwable) : Internal
  }
}

internal sealed interface ProfileCommand {

  object ObserveProfile : ProfileCommand
}

@Immutable
internal sealed interface ProfileEffect {

  data class ShowError(val error: Throwable) : ProfileEffect

  data class OpenTeamRegistration(val profile: Profile) : ProfileEffect
  data class OpenEditProfile(val profile: Profile) : ProfileEffect
}
