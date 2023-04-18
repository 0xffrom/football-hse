package andryuh.football.domain_search

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
class SearchRepository @Inject constructor(private val api: SearchApi) {

  private val teamApplicationsCache = MutableStateFlow<List<TeamApplication>?>(null)

  fun observeTeamApplications(): Flow<List<TeamApplication>> {
    CoroutineScope(Dispatchers.IO).launch { updateTeamApplicationsCache() }

    return teamApplicationsCache.filterNotNull()
  }
  private suspend fun updateTeamApplicationsCache() {
    val newValue = api.getTeamApplications()

    teamApplicationsCache.emit(newValue)
  }
}
