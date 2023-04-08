package goshka133.football.domain_auth.session

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.terrakok.modo.stack.forward
import goshka133.football.core_navigation.RouterProvider
import goshka133.football.domain_auth.AuthApi
import goshka133.football.domain_auth.AuthFeatureApi
import goshka133.football.domain_auth.dto.SessionResponse
import goshka133.football.domain_auth.dto.UpdateSessionRequestBody
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Provider

internal class UserSessionStorage
@Inject
constructor(
  private val api: dagger.Lazy<AuthApi>,
  private val dataStore: DataStore<Preferences>,
  private val authFeatureApi: AuthFeatureApi,
  private val routerHolder: RouterProvider,
) : UserSessionUpdater, UserSessionProvider {

  private val refreshTokenPrefsKey = stringPreferencesKey("refreshToken")
  private val phoneNumberPrefsKey = stringPreferencesKey("phoneNumber")

  override val sessionFlow: MutableStateFlow<UserSession?> = MutableStateFlow(null)

  override suspend fun updateSession() {
    val data = dataStore.data.first()

    val refreshToken = data[refreshTokenPrefsKey]
    val phoneNumber = data[phoneNumberPrefsKey]

    if (refreshToken == null || phoneNumber == null) {
      routerHolder.router?.forward(authFeatureApi.getScreen())
    } else {

      val sessionResponse =
        api.get().updateSession(
          requestBody =
          UpdateSessionRequestBody(
            refreshToken = refreshToken,
            phoneNumber = phoneNumber,
          )
        )

      updateSession(sessionResponse)
    }
  }

  override suspend fun updateSession(sessionResponse: SessionResponse) {
    sessionFlow.emit(sessionResponse.toUserSession())

    dataStore.edit { prefs -> prefs[phoneNumberPrefsKey] = sessionResponse.phoneNumber }
    dataStore.edit { prefs -> prefs[refreshTokenPrefsKey] = sessionResponse.refreshToken }
  }

  private fun SessionResponse.toUserSession(): UserSession {
    return UserSession(
      accessToken = token,
      phoneNumber = phoneNumber,
      isCaptain = isCaptain,
      isRegistered = isRegistered
    )
  }
}
