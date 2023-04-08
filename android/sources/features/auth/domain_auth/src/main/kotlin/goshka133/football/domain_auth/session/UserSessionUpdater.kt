package goshka133.football.domain_auth.session

import goshka133.football.domain_auth.dto.SessionResponse

interface UserSessionUpdater {

  suspend fun updateSession()

  suspend fun updateSession(sessionResponse: SessionResponse)
}
