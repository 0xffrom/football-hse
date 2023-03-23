package goshka133.football.feature_profile.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.github.terrakok.modo.stack.forward
import goshka133.football.core_elmslie.rememberEventReceiver
import goshka133.football.core_elmslie.rememberStore
import goshka133.football.core_navigation.LocalRouter
import goshka133.football.feature_profile.screens.profile.presentation.ProfileEffect
import goshka133.football.feature_profile.screens.profile.presentation.ProfileEvent
import goshka133.football.feature_profile.screens.profile.presentation.ProfileStoreFactory
import goshka133.football.feature_profile.screens.profile.ui.ProfileCard
import goshka133.football.feature_profile.screens.profile.ui.TeamCreationApplicationCard
import goshka133.football.feature_profile.screens.team_registration.TeamRegistrationScreen
import goshka133.football.ui_kit.BaseScreen
import goshka133.football.ui_kit.theme.FootballColors
import goshka133.football.ui_kit.theme.Style16500
import goshka133.football.ui_kit.theme.Style16600
import goshka133.football.ui_kit.theme.Style19600
import kotlinx.parcelize.Parcelize

@Parcelize
internal class ProfileScreen : BaseScreen() {

  @Composable
  override fun Content() {
    val store =
      rememberStore(
        storeFactoryClass = ProfileStoreFactory::class.java,
        storeProvider = { storeFactory, _ -> storeFactory.create() },
      )
    val state by store.states().collectAsState(store.currentState)
    val eventReceiver = store.rememberEventReceiver()
    val router = LocalRouter.current

    LaunchedEffect(key1 = store) {
      store.effects().collect { effect ->
        when (effect) {
          is ProfileEffect.OpenTeamRegistration ->
            router.forward(
              TeamRegistrationScreen(
                effect.profileFullName,
              )
            )
        }
      }
    }

    Scaffold(
      topBar = {
        Row(
          modifier = Modifier.systemBarsPadding().heightIn(min = 44.dp).padding(horizontal = 16.dp),
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
      LazyColumn(contentPadding = contentPadding) {
        item { Spacer(modifier = Modifier.height(12.dp)) }
        item {
          ProfileCard(
            modifier = Modifier.padding(horizontal = 16.dp),
            profile = state.profile,
          )
          Spacer(modifier = Modifier.height(10.dp))
        }
        item {
          TeamCreationApplicationCard(
            modifier = Modifier.padding(horizontal = 16.dp),
            teamApplication = state.teamApplication,
            onClick = { eventReceiver.invoke(ProfileEvent.Ui.Click.TeamApplication) },
          )
          Spacer(modifier = Modifier.height(24.dp))
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
        item {
          Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
          ) {
            Image(
              modifier = Modifier.size(250.dp, 200.dp),
              painter =
                painterResource(
                  id = goshka133.football.ui_kit.R.drawable.img_empty_applications,
                ),
              contentDescription = null,
            )
          }
        }
      }
    }
  }
}
