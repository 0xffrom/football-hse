package goshka133.football.feature_team.screens.team_details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.github.terrakok.modo.stack.back
import goshka133.football.core_elmslie.rememberEventReceiver
import goshka133.football.core_elmslie.rememberStore
import goshka133.football.core_models.mapToTitle
import goshka133.football.core_navigation.LocalRouter
import goshka133.football.domain_team.TeamApplication
import goshka133.football.ui_kit.toolbar.Toolbar
import goshka133.football.feature_team.screens.team_details.presentation.TeamApplicationDetailsEffect
import goshka133.football.feature_team.screens.team_details.presentation.TeamApplicationDetailsEvent
import goshka133.football.feature_team.screens.team_details.presentation.TeamApplicationDetailsStoreFactory
import goshka133.football.ui_kit.BaseScreen
import goshka133.football.ui_kit.theme.FootballColors
import goshka133.football.ui_kit.theme.Style12500
import goshka133.football.ui_kit.theme.Style14400
import goshka133.football.ui_kit.theme.Style19600
import kotlinx.parcelize.Parcelize

@Parcelize
internal class TeamApplicationDetailsScreen(
  private val application: TeamApplication,
) : BaseScreen() {

  @Composable
  override fun Content() {
    val store =
      rememberStore(
        storeFactoryClass = TeamApplicationDetailsStoreFactory::class.java,
        storeProvider = { storeFactory, _ ->
          storeFactory.create(
            application = application,
          )
        },
      )

    val state by store.states().collectAsState(store.currentState)
    val eventReceiver = store.rememberEventReceiver()
    val router = LocalRouter.current

    LaunchedEffect(key1 = store) {
      store.effects().collect { effect ->
        when (effect) {
          is TeamApplicationDetailsEffect.Close -> router.back()
        }
      }
    }

    BackHandler { eventReceiver.invoke(TeamApplicationDetailsEvent.Ui.Click.Back) }

    Scaffold(
      topBar = {
        Toolbar(
          onBackClick = { eventReceiver.invoke(TeamApplicationDetailsEvent.Ui.Click.Back) },
          title = "Заявка"
        )
      },
    ) { contentPadding ->
      val application = state.teamApplication

      val positionTitles =
        remember(application.playerPosition) { application.playerPosition.mapToTitle() }

      LazyColumn(
        contentPadding = contentPadding,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        item {
          AsyncImage(
            modifier = Modifier.size(74.dp),
            model = application.imageUrl,
            contentDescription = null,
          )
          Spacer(modifier = Modifier.height(20.dp))
        }
        item {
          Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = application.name,
            color = FootballColors.Text.Primary,
            style = Style19600,
          )
          Spacer(modifier = Modifier.height(20.dp))
        }
        if (positionTitles.isNotEmpty()) {
          item {
            SectionCard(
              modifier = Modifier.padding(horizontal = 16.dp),
              title = "Позиции",
              contentPadding = PaddingValues(vertical = 20.dp),
              titlePadding = PaddingValues(horizontal = 20.dp),
            ) {
              LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
              ) {
                items(positionTitles) { positionTitle ->
                  Text(
                    modifier =
                      Modifier.background(
                          color = Color(0x1A4258FE),
                          shape = RoundedCornerShape(4.dp),
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp),
                    text = positionTitle,
                    color = Color(0xFF3461FD),
                    style =
                      TextStyle(
                        fontWeight = FontWeight.W500,
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        letterSpacing = 1.sp,
                      ),
                  )
                }
              }
            }
            Spacer(modifier = Modifier.height(12.dp))
          }
        }
        if (application.contact?.isNotBlank() == true) {
          item {
            SectionCard(
              modifier = Modifier.padding(horizontal = 16.dp),
              title = "Контактная история"
            ) {
              Text(
                text = application.contact.orEmpty(),
                color = FootballColors.Text.Primary,
                style = Style14400,
              )
            }
            Spacer(modifier = Modifier.height(12.dp))
          }
        }
        if (application.description?.isNotBlank() == true) {
          item {
            SectionCard(
              modifier = Modifier.padding(horizontal = 16.dp),
              title = "Дополнительная информация",
            ) {
              Text(
                text = application.description.orEmpty(),
                color = FootballColors.Text.Primary,
                style = Style14400,
              )
            }
          }
        }
        item { Spacer(modifier = Modifier.height(20.dp)) }
      }
    }
  }
}

@Composable
private fun SectionCard(
  modifier: Modifier = Modifier,
  title: String,
  contentPadding: PaddingValues = PaddingValues(20.dp),
  titlePadding: PaddingValues = PaddingValues(0.dp),
  content: @Composable () -> Unit,
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(12.dp),
    backgroundColor = FootballColors.Surface1,
    elevation = 0.dp,
  ) {
    Column(
      modifier = Modifier.padding(contentPadding),
      verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      Text(
        modifier = Modifier.padding(titlePadding),
        text = title,
        color = FootballColors.Text.Secondary,
        style = Style12500,
      )
      content.invoke()
    }
  }
}
