package andryuh.football.domain_search

import andryuh.football.core_models.PlayerPosition
import andryuh.football.core_models.Tournament
import andryuh.football.core_network.ext.throwExceptionIfError
import andryuh.football.domain_profile.ProfileRepository
import andryuh.football.domain_search.dto.CreatePlayerApplication
import andryuh.football.domain_team.dto.TeamApplication
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Singleton
class SearchRepository
@Inject
constructor(
  private val api: SearchApi,
  private val profileRepository: ProfileRepository,
) {

  private val teamApplicationsCache = MutableStateFlow<List<TeamApplication>?>(null)

  fun observeTeamApplications(): Flow<List<TeamApplication>> {
    CoroutineScope(Dispatchers.IO).launch { updateTeamApplicationsCache() }

    return teamApplicationsCache.filterNotNull()
  }

  suspend fun createCreatePlayerApplication(
    tournaments: List<Tournament>,
    positions: List<PlayerPosition>,
  ) {
    val profile = profileRepository.getProfile()

    val application =
      CreatePlayerApplication(
        phoneNumber = profile.phoneNumber,
        footballExperience = profile.footballExperience,
        tournamentsExperience = profile.tournamentsExperience,
        contact = profile.contactInfo,
        name = profile.fullName,
        footballPosition = positions,
        preferredTournaments = tournaments,
        imageUrl = profile.imageUrl,
      )

    api.createPlayerApplication(application).throwExceptionIfError()
  }

  private suspend fun updateTeamApplicationsCache() {
    val newValue = api.getTeamApplications()

    teamApplicationsCache.emit(newValue)
  }
}
