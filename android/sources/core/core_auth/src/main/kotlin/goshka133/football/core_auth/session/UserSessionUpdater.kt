package goshka133.football.core_auth.session

interface UserSessionUpdater {

  suspend fun updateSession()

  suspend fun updateSession(sessionResponse: UserSession)
}
