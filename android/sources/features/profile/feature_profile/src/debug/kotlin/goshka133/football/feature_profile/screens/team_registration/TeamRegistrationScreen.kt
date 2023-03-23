package goshka133.football.feature_profile.screens.team_registration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.terrakok.modo.stack.back
import goshka133.football.core_elmslie.rememberEventReceiver
import goshka133.football.core_elmslie.rememberStore
import goshka133.football.core_navigation.LocalRouter
import goshka133.football.feature_profile.R
import goshka133.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEffect
import goshka133.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEvent
import goshka133.football.feature_profile.screens.team_registration.presentation.TeamRegistrationStoreFactory
import goshka133.football.ui_kit.BaseScreen
import goshka133.football.ui_kit.theme.FootballColors
import goshka133.football.ui_kit.theme.Style16500
import kotlinx.parcelize.Parcelize

@Parcelize
internal class TeamRegistrationScreen(
  private val profileFullName: String,
) : BaseScreen() {

  @Composable
  override fun Content() {
    val store =
      rememberStore(
        storeFactoryClass = TeamRegistrationStoreFactory::class.java,
        storeProvider = { storeFactory, _ ->
          storeFactory.create(
            profileFullName = profileFullName,
          )
        },
      )

    val state by store.states().collectAsState(store.currentState)
    val eventReceiver = store.rememberEventReceiver()
    val router = LocalRouter.current

    LaunchedEffect(key1 = store) {
      store.effects().collect { effect ->
        when (effect) {
          is TeamRegistrationEffect.Close -> router.back()
        }
      }
    }

    Scaffold(
      topBar = {
        Box(
          modifier = Modifier.systemBarsPadding().heightIn(min = 44.dp).padding(horizontal = 16.dp),
          contentAlignment = Alignment.CenterStart,
        ) {
          IconButton(onClick = { eventReceiver.invoke(TeamRegistrationEvent.Ui.Click.Back) }) {
            Icon(
              painter = painterResource(id = goshka133.football.ui_kit.R.drawable.ic_24_back),
              contentDescription = null,
              tint = FootballColors.Icons.Primary,
            )
          }
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Регистрация команды",
            textAlign = TextAlign.Center,
            color = FootballColors.Text.Primary,
            style = Style16500,
          )
        }
      }
    ) { contentPadding ->
      LazyColumn(contentPadding = contentPadding) {}
    }
  }
}
