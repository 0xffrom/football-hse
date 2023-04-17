package andryuh.football.domain_team

import android.content.Context
import android.net.Uri
import andryuh.football.core_auth.PhoneStorage
import andryuh.football.core_network.ext.throwExceptionIfError
import andryuh.football.core_network.ext.toRequestBody
import andryuh.football.domain_team.dto.CreateTeamBody
import andryuh.football.domain_team.dto.CreateTeamRequestBody
import andryuh.football.domain_team.dto.Team
import andryuh.football.domain_team.dto.TeamCreationApplicationStatus
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

@Singleton
class TeamRepository
@Inject
constructor(
  private val teamApi: TeamApi,
  private val phoneNumberStorage: PhoneStorage,
  private val context: Context,
) {

  private val teamCreationCache = MutableStateFlow<TeamCreationApplicationStatus?>(null)

  fun observeTeamCreationApplication(): Flow<TeamCreationApplicationStatus> {
    CoroutineScope(Dispatchers.IO).launch { updateTeamCreationApplication() }

    return teamCreationCache.filterNotNull()
  }
  suspend fun createTeam(body: CreateTeamBody) {
    val requestBody =
      CreateTeamRequestBody.create(
        body = body,
        phoneNumber = phoneNumberStorage.getPhoneRequired(),
      )

    teamApi.createTeam(requestBody).throwExceptionIfError()

    updateTeamCreationApplication()
  }

  suspend fun uploadImage(imageUri: Uri) {
    val body = imageUri.toRequestBody(context)

    val team = requireNotNull(getTeam())

    val filePart =
      MultipartBody.Part.createFormData(
        /* name = */ "image",
        /* filename = */ "team_image${team.id}",
        /* body = */ body,
      )

    teamApi.updatePhoto(team.id, filePart).throwExceptionIfError()
    updateTeamCreationApplication()
  }

  private suspend fun updateTeamCreationApplication() {
    val team = getTeam()

    val status =
      if (team == null) {
        TeamCreationApplicationStatus.NotRegistered
      } else {
        TeamCreationApplicationStatus.Registered(team)
      }

    teamCreationCache.emit(status)
  }
  private suspend fun getTeam(): Team? {
    return teamApi.getTeams().firstOrNull { team ->
      team.captainPhoneNumber == phoneNumberStorage.getPhoneRequired()
    }
  }
}
