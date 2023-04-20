package andryuh.football.feature_profile.screens.profile.presentation

import androidx.compose.runtime.Immutable
import andryuh.football.core_kotlin.Resource
import andryuh.football.domain_profile.dto.PlayerApplication
import andryuh.football.domain_profile.dto.Profile
import andryuh.football.domain_team.dto.Team
import andryuh.football.domain_team.dto.TeamCreationApplicationStatus

@Immutable
internal data class ProfileState(
  val profile: Resource<Profile> = Resource.Loading,
  val teamApplication: Resource<TeamCreationApplicationStatus> = Resource.Loading,
  val applications: Resource<List<PlayerApplication>> = Resource.Loading,
)

internal sealed interface ProfileEvent {

  sealed interface Ui : ProfileEvent {
    object System {
      object Start : Ui
    }

    object Click {

      object Leave : Ui
      object TeamApplication : Ui
      object EditClick : Ui

      data class PlayerApplicationCard(val application: PlayerApplication) : Ui
    }
  }

  sealed interface Internal : ProfileEvent {

    data class ObserveProfileSuccess(val profile: Profile) : Internal
    data class ObserveProfileError(val error: Throwable) : Internal

    data class ObserveTeamCreationStatusSuccess(val status: TeamCreationApplicationStatus) :
      Internal
    data class ObserveTeamCreationStatusError(val error: Throwable) : Internal

    data class ObservePlayerApplicationsSuccess(val applications: List<PlayerApplication>) :
      Internal
    data class ObservePlayerApplicationsError(val error: Throwable) : Internal
  }
}

internal sealed interface ProfileCommand {

  object ObserveProfile : ProfileCommand

  object ObserveTeamCreationStatus : ProfileCommand

  object ObservePlayerApplications : ProfileCommand

  object ClearSession : ProfileCommand
}

@Immutable
internal sealed interface ProfileEffect {

  data class ShowError(val error: Throwable) : ProfileEffect

  data class OpenTeamRegistration(val profile: Profile) : ProfileEffect
  data class OpenTeamDetails(val team: Team) : ProfileEffect
  data class OpenEditProfile(val profile: Profile) : ProfileEffect
  data class OpenProfileApplication(val application: PlayerApplication) : ProfileEffect

  object OpenAuth : ProfileEffect
}
