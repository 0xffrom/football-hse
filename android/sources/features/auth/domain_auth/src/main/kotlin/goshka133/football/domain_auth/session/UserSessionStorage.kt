package goshka133.football.domain_auth.session

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.github.terrakok.modo.stack.forward
import goshka133.football.core_auth.dto.UpdateUserSessionRequestBody
import goshka133.football.core_auth.feature_api.RefreshSessionFeatureApi
import goshka133.football.core_auth.session.UserSession
import goshka133.football.core_auth.session.UserSessionApi
import goshka133.football.core_auth.session.UserSessionProvider
import goshka133.football.core_auth.session.UserSessionUpdater
import goshka133.football.core_navigation.RouterProvider
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first

@Singleton
internal class UserSessionStorage
@Inject
constructor(
  private val api: dagger.Lazy<UserSessionApi>,
  private val dataStore: DataStore<Preferences>,
  private val authFeatureApi: RefreshSessionFeatureApi,
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
        api
          .get()
          .updateSession(
            requestBody =
              UpdateUserSessionRequestBody(
                refreshToken = refreshToken,
                phoneNumber = phoneNumber,
              )
          )

      updateSession(sessionResponse)
    }
  }

  override suspend fun updateSession(sessionResponse: UserSession) {
    sessionFlow.emit(sessionResponse)

    dataStore.edit { prefs -> prefs[phoneNumberPrefsKey] = sessionResponse.phoneNumber }
    dataStore.edit { prefs -> prefs[refreshTokenPrefsKey] = sessionResponse.refreshToken }
  }
}
