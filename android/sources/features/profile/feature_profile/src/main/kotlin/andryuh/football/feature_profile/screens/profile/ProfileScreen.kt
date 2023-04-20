package andryuh.football.feature_profile.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import andryuh.football.core_di.rememberDependencies
import andryuh.football.core_elmslie.rememberEventReceiver
import andryuh.football.core_elmslie.rememberStore
import andryuh.football.core_kotlin.Resource
import andryuh.football.core_navigation.LocalRouter
import andryuh.football.feature_profile.di.ProfileFeatureDependencies
import andryuh.football.feature_profile.screens.edit_profile.EditProfileScreen
import andryuh.football.feature_profile.screens.profile.presentation.ProfileEffect
import andryuh.football.feature_profile.screens.profile.presentation.ProfileEvent.Ui.Click
import andryuh.football.feature_profile.screens.profile.presentation.ProfileStoreFactory
import andryuh.football.feature_profile.screens.profile.ui.ProfileCard
import andryuh.football.feature_profile.screens.profile.ui.PlayerApplicationCard
import andryuh.football.feature_profile.screens.profile.ui.PlayerApplicationCardShimmer
import andryuh.football.feature_profile.screens.profile.ui.TeamCreationApplicationCard
import andryuh.football.feature_profile.screens.team_registration.TeamRegistrationScreen
import andryuh.football.ui_kit.BaseScreen
import andryuh.football.ui_kit.R
import andryuh.football.ui_kit.snack_bar.LocalSnackBarHostState
import andryuh.football.ui_kit.theme.FootballColors
import andryuh.football.ui_kit.theme.Style16500
import andryuh.football.ui_kit.theme.Style16600
import andryuh.football.ui_kit.theme.Style19600
import com.github.terrakok.modo.stack.forward
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
internal class ProfileScreen : BaseScreen() {

  @Composable
  override fun Content() {
    val store =
      rememberStore(
        storeFactoryClass = ProfileStoreFactory::class.java,
        storeProvider = { storeFactory, _ -> storeFactory.create() },
        savedStateRegistryOwner = LocalSavedStateRegistryOwner.current,
        vmStoreOwner = LocalViewModelStoreOwner.current!!,
      )
    val state by store.states().collectAsState(store.currentState)
    val eventReceiver = store.rememberEventReceiver()
    val router = LocalRouter.current
    val snackBarHostState = LocalSnackBarHostState.current
    val dependencies = rememberDependencies<ProfileFeatureDependencies>()

    LaunchedEffect(key1 = store) {
      store.effects().collect { effect ->
        when (effect) {
          is ProfileEffect.OpenTeamRegistration -> {
            router.forward(TeamRegistrationScreen(profile = effect.profile))
          }
          is ProfileEffect.OpenEditProfile -> {
            router.forward(EditProfileScreen(profile = effect.profile))
          }
          is ProfileEffect.ShowError -> {
            CoroutineScope(Dispatchers.Main).launch {
              effect.error.message?.let { snackBarHostState.showSnackbar(it) }
            }
          }
          is ProfileEffect.OpenTeamDetails -> {
            router.forward(dependencies.teamFeatureApi.getTeamDetails(effect.team))
          }
        }
      }
    }

    Scaffold(
      topBar = {
        Row(
          modifier = Modifier.statusBarsPadding().heightIn(min = 44.dp).padding(horizontal = 16.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          val exitSize = remember { mutableStateOf(IntSize.Zero) }
          val exitSizeXDp = with(LocalDensity.current) { exitSize.value.width.toDp() }

          Text(
            modifier = Modifier.weight(1f).offset(y = 0.dp, x = exitSizeXDp / 2),
            text = "Профиль",
            textAlign = TextAlign.Center,
            color = FootballColors.Text.Primary,
            style = Style16500,
          )
          Text(
            modifier = Modifier.onSizeChanged { exitSize.value = it },
            text = "Выйти",
            color = FootballColors.Primary,
            style = Style16600,
          )
        }
      }
    ) { contentPadding ->
      LazyColumn(modifier = Modifier.padding(contentPadding), state = rememberLazyListState()) {
        item { Spacer(modifier = Modifier.height(12.dp)) }
        when (val profileResource = state.profile) {
          is Resource.Data,
          is Resource.Loading -> {
            item {
              ProfileCard(
                modifier = Modifier.padding(horizontal = 16.dp),
                profile = profileResource.value,
                onEditClick = { eventReceiver.invoke(Click.EditClick) }
              )
              Spacer(modifier = Modifier.height(10.dp))
            }
          }
          is Resource.Error -> {}
        }
        when (val applicationResource = state.teamApplication) {
          is Resource.Loading -> {
            item {
              Card(
                modifier = Modifier.fillMaxWidth().height(64.dp).padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color(0xFFF5F9FE),
                elevation = 0.dp,
              ) {
                Box(
                  modifier =
                    Modifier.fillMaxSize()
                      .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.fade(),
                      ),
                )
              }
              Spacer(modifier = Modifier.height(24.dp))
            }
          }
          is Resource.Data -> {
            item {
              TeamCreationApplicationCard(
                modifier = Modifier.padding(horizontal = 16.dp),
                teamApplication = applicationResource.value,
                onClick = { eventReceiver.invoke(Click.TeamApplication) },
              )
              Spacer(modifier = Modifier.height(24.dp))
            }
          }
          is Resource.Error -> {
            item { Spacer(modifier = Modifier.height(12.dp)) }
          }
        }
        item {
          Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Мои заявки",
            color = FootballColors.Text.Primary,
            textAlign = TextAlign.Start,
            style = Style19600,
          )
          Spacer(modifier = Modifier.height(16.dp))
        }
        when (val applicationsResource = state.applications) {
          is Resource.Loading -> {
            items(2) {
              Spacer(modifier = Modifier.height(12.dp))
              PlayerApplicationCardShimmer(
                modifier = Modifier.padding(horizontal = 16.dp),
              )
            }
            item { Spacer(modifier = Modifier.height(20.dp)) }
          }
          is Resource.Data -> {
            if (applicationsResource.data.isEmpty()) {
              item {
                Box(
                  modifier = Modifier.fillMaxWidth(),
                  contentAlignment = Alignment.Center,
                ) {
                  Image(
                    modifier = Modifier.size(250.dp, 200.dp),
                    painter =
                      painterResource(
                        id = R.drawable.img_empty_applications,
                      ),
                    contentDescription = null,
                  )
                }
              }
            } else {
              items(applicationsResource.data) { application ->
                Spacer(modifier = Modifier.height(12.dp))
                PlayerApplicationCard(
                  modifier = Modifier.padding(horizontal = 16.dp),
                  application = application
                )
              }
              item { Spacer(modifier = Modifier.height(20.dp)) }
            }
          }
          is Resource.Error -> {}
        }
      }
    }
  }
}
