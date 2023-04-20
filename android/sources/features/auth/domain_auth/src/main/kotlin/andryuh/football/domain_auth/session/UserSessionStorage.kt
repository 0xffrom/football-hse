package andryuh.football.domain_auth.session

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import andryuh.football.core_auth.PhoneStorage
import andryuh.football.core_auth.dto.UpdateUserSessionRequestBody
import andryuh.football.core_auth.feature_api.RefreshSessionFeatureApi
import andryuh.football.core_auth.session.UserSession
import andryuh.football.core_auth.session.UserSessionApi
import andryuh.football.core_auth.session.UserSessionProvider
import andryuh.football.core_auth.session.UserSessionUpdater
import andryuh.football.core_navigation.RouterProvider
import com.github.terrakok.modo.stack.forward
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
  private val phoneStorage: PhoneStorage,
) : UserSessionUpdater, UserSessionProvider {

  private val refreshTokenPrefsKey = stringPreferencesKey("refreshToken")

  override val sessionFlow: MutableStateFlow<UserSession?> = MutableStateFlow(null)

  override suspend fun updateSession() {
    val data = dataStore.data.first()

    val refreshToken = data[refreshTokenPrefsKey]
    val phoneNumber = phoneStorage.getPhone()

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

  override suspend fun updateSession(sessionResponse: UserSession?) {
    sessionFlow.emit(sessionResponse)

    phoneStorage.updatePhone(sessionResponse?.phoneNumber)
    dataStore.edit { prefs ->
      prefs[refreshTokenPrefsKey] = sessionResponse?.refreshToken.orEmpty()
    }
  }
}
