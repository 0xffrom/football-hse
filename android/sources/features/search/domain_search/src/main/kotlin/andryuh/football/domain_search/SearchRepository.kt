package andryuh.football.domain_search

import andryuh.football.core_auth.PhoneStorage
import andryuh.football.core_models.PlayerPosition
import andryuh.football.core_models.Tournament
import andryuh.football.core_network.ext.throwExceptionIfError
import andryuh.football.domain_profile.ProfileRepository
import andryuh.football.domain_profile.dto.PlayerApplication
import andryuh.football.domain_search.dto.CreatePlayerApplication
import andryuh.football.domain_team.dto.TeamApplication
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Singleton
class SearchRepository
@Inject
constructor(
  private val api: SearchApi,
  private val profileRepository: ProfileRepository,
  private val phoneStorage: PhoneStorage,
) {

  private val teamApplicationsCache = MutableStateFlow<List<TeamApplication>?>(null)
  private val playerApplicationsCache = MutableStateFlow<List<PlayerApplication>?>(null)

  fun observeTeamApplications(): Flow<List<TeamApplication>> {
    CoroutineScope(Dispatchers.IO).launch { updateTeamApplicationsCache() }

    return teamApplicationsCache.filterNotNull()
  }

  fun observePlayerApplications(): Flow<List<PlayerApplication>> {
    CoroutineScope(Dispatchers.IO).launch { updatePlayerApplicationsCache() }

    return playerApplicationsCache.filterNotNull().map { applications ->
      val phoneNumber = phoneStorage.getPhoneRequired()

      applications.filter { application -> application.phoneNumber == phoneNumber }
    }
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
    updatePlayerApplicationsCache()
  }

  private suspend fun updateTeamApplicationsCache() {
    val newValue = api.getTeamApplications()

    teamApplicationsCache.emit(newValue)
  }

  private suspend fun updatePlayerApplicationsCache() {
    val newValue = api.getPlayerApplications()

    playerApplicationsCache.emit(newValue)
  }
}
