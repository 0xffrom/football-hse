package andryuh.football.core_auth.session

import kotlinx.coroutines.flow.StateFlow

interface UserSessionProvider {

  val sessionFlow: StateFlow<UserSession?>
}
