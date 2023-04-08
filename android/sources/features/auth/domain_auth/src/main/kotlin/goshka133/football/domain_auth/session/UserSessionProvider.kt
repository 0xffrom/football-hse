package goshka133.football.domain_auth.session

import kotlinx.coroutines.flow.StateFlow

interface UserSessionProvider {

  val sessionFlow: StateFlow<UserSession?>
}
