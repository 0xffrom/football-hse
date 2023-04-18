package andryuh.football.feature_team.screens.team_application_details

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import andryuh.football.core_elmslie.rememberEventReceiver
import andryuh.football.core_elmslie.rememberStore
import andryuh.football.core_models.joinTitleToString
import andryuh.football.core_models.mapToTitle
import andryuh.football.core_navigation.LocalRouter
import andryuh.football.domain_team.dto.TeamApplication
import andryuh.football.feature_team.screens.team_application_details.presentation.TeamApplicationDetailsEffect
import andryuh.football.feature_team.screens.team_application_details.presentation.TeamApplicationDetailsEvent
import andryuh.football.feature_team.screens.team_application_details.presentation.TeamApplicationDetailsStoreFactory
import andryuh.football.ui_kit.BaseScreen
import andryuh.football.ui_kit.items.SectionCard
import andryuh.football.ui_kit.theme.FootballColors
import andryuh.football.ui_kit.theme.Style19600
import andryuh.football.ui_kit.toolbar.Toolbar
import coil.compose.AsyncImage
import com.github.terrakok.modo.stack.back
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
        modifier = Modifier.padding(contentPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        item {
          AsyncImage(
            modifier = Modifier.size(74.dp).clip(CircleShape),
            model = application.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
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
        if (application.tournaments.isNotEmpty()) {
          item {
            SectionCard(modifier = Modifier.padding(horizontal = 16.dp), title = "Турниры") {
              SectionText(value = application.tournaments.joinTitleToString())
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
              SectionText(value = application.contact.orEmpty())
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
              SectionText(value = application.description.orEmpty())
            }
          }
        }
        item { Spacer(modifier = Modifier.height(20.dp)) }
      }
    }
  }
}
