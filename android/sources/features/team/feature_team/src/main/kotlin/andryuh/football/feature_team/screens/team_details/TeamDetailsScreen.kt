package andryuh.football.feature_team.screens.team_details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import andryuh.football.core_elmslie.rememberEventReceiver
import andryuh.football.core_elmslie.rememberStore
import andryuh.football.core_navigation.LocalRouter
import andryuh.football.domain_team.dto.Team
import andryuh.football.domain_team.dto.TeamStatus
import andryuh.football.domain_team.dto.toLocalizedString
import andryuh.football.feature_team.screens.team_details.presentation.TeamDetailsEffect
import andryuh.football.feature_team.screens.team_details.presentation.TeamDetailsEvent.Ui
import andryuh.football.feature_team.screens.team_details.presentation.TeamDetailsStoreFactory
import andryuh.football.ui_kit.BaseScreen
import andryuh.football.ui_kit.R
import andryuh.football.ui_kit.button.BottomBarStack
import andryuh.football.ui_kit.button.FButton
import andryuh.football.ui_kit.items.SectionCard
import andryuh.football.ui_kit.snack_bar.LocalSnackBarHostState
import andryuh.football.ui_kit.theme.FootballColors
import andryuh.football.ui_kit.theme.Style14400
import andryuh.football.ui_kit.theme.Style19600
import andryuh.football.ui_kit.toolbar.Toolbar
import coil.compose.AsyncImage
import com.github.terrakok.modo.stack.back
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
internal class TeamDetailsScreen(
  private val team: Team,
) : BaseScreen() {

  @Composable
  override fun Content() {
    val store =
      rememberStore(
        storeFactoryClass = TeamDetailsStoreFactory::class.java,
        storeProvider = { storeFactory, _ ->
          storeFactory.create(
            team = team,
          )
        },
      )

    val state by store.states().collectAsState(store.currentState)
    val eventReceiver = store.rememberEventReceiver()
    val router = LocalRouter.current

    val snackBarHostState = LocalSnackBarHostState.current

    LaunchedEffect(key1 = store) {
      store.effects().collect { effect ->
        when (effect) {
          is TeamDetailsEffect.Close -> router.back()
          is TeamDetailsEffect.ShowError -> {
            CoroutineScope(Dispatchers.Main).launch {
              effect.error.message?.let { snackBarHostState.showSnackbar(it) }
            }
          }
        }
      }
    }

    BackHandler { eventReceiver.invoke(Ui.Click.Back) }

    Scaffold(
      topBar = {
        Toolbar(onBackClick = { eventReceiver.invoke(Ui.Click.Back) }, title = "Команда")
      },
      bottomBar = {
        BottomBarStack {
          FButton(
            text =
              when (state.team.status) {
                TeamStatus.Verified -> "Удалить команду"
                else -> "Удалить заявку"
              },
            isLoading = state.isLoading,
          ) {
            eventReceiver(Ui.Click.Delete)
          }
        }
      },
    ) { contentPadding ->
      val team = state.team

      LazyColumn(
        modifier = Modifier.padding(contentPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        item {
          Spacer(modifier = Modifier.height(20.dp))
          AsyncImage(
            modifier = Modifier.size(74.dp).clip(CircleShape),
            model = team.logo,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.ic_60_team_placeholder),
          )
          Spacer(modifier = Modifier.height(20.dp))
        }
        item {
          Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = team.name,
            color = FootballColors.Text.Primary,
            style = Style19600,
          )
          Spacer(modifier = Modifier.height(12.dp))
        }
        item {
          val statusColor =
            when (team.status) {
              TeamStatus.Verified -> FootballColors.Accent.Green
              TeamStatus.Declined -> FootballColors.Accent.Red
              TeamStatus.OnValidation -> FootballColors.Accent.Orange
            }

          Box(
            modifier =
              Modifier.padding(horizontal = 16.dp)
                .background(
                  color = statusColor.copy(alpha = 0.1f),
                  shape = RoundedCornerShape(4.dp),
                )
          ) {
            Text(
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
              text = team.status.toLocalizedString(),
              style = Style14400,
              color = statusColor,
            )
          }
          Spacer(modifier = Modifier.height(20.dp))
        }
        if (team.captainName.isNotBlank()) {
          item {
            SectionCard(
              modifier = Modifier.padding(horizontal = 16.dp),
              title = "Капитан команды"
            ) {
              SectionText(value = team.captainName)
            }
            Spacer(modifier = Modifier.height(12.dp))
          }
        }
        if (team.about?.isNotBlank() == true) {
          item {
            SectionCard(
              modifier = Modifier.padding(horizontal = 16.dp),
              title = "Капитан команды"
            ) {
              SectionText(value = team.about.orEmpty())
            }
            Spacer(modifier = Modifier.height(12.dp))
          }
        }
        item { Spacer(modifier = Modifier.height(20.dp)) }
      }
    }
  }
}
