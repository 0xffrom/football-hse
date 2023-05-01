package andryuh.football.feature_profile.screens.profile_application

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import andryuh.football.core_di.rememberDependencies
import andryuh.football.core_elmslie.rememberEventReceiver
import andryuh.football.core_elmslie.rememberStore
import andryuh.football.core_models.joinTitleToString
import andryuh.football.core_models.mapToTitle
import andryuh.football.core_navigation.LocalRouter
import andryuh.football.domain_main.CommonBottomBarTabType
import andryuh.football.domain_profile.dto.PlayerApplication
import andryuh.football.feature_profile.di.ProfileFeatureDependencies
import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationEffect
import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationEvent.Ui
import andryuh.football.feature_profile.screens.profile_application.presentation.ProfileApplicationStoreFactory
import andryuh.football.ui_kit.R.*
import andryuh.football.ui_kit.button.BottomBarStack
import andryuh.football.ui_kit.button.FButton
import andryuh.football.ui_kit.items.SectionCard
import andryuh.football.ui_kit.snack_bar.LocalSnackBarHostState
import andryuh.football.ui_kit.snack_bar.showError
import andryuh.football.ui_kit.theme.FootballColors
import andryuh.football.ui_kit.theme.Style19600
import andryuh.football.ui_kit.toolbar.Toolbar
import coil.compose.AsyncImage
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.back
import com.github.terrakok.modo.stack.backToRoot
import kotlinx.parcelize.Parcelize

@Parcelize
internal class ProfileApplicationScreen(
  private val playerApplication: PlayerApplication,
  private val isChatSupport: Boolean,
) : Screen(generateScreenKey()) {

  @Composable
  override fun Content() {
    val store =
      rememberStore(
        storeFactoryClass = ProfileApplicationStoreFactory::class.java,
        storeProvider = { storeFactory, _ -> storeFactory.create(playerApplication) },
      )

    val state by store.states().collectAsState(store.currentState)
    val eventReceiver = store.rememberEventReceiver()
    val router = LocalRouter.current

    val dependencies: ProfileFeatureDependencies = rememberDependencies()
    val snackbarHostState = LocalSnackBarHostState.current

    LaunchedEffect(key1 = store) {
      store.effects().collect { effect ->
        when (effect) {
          is ProfileApplicationEffect.Close -> router.back()
          is ProfileApplicationEffect.OpenChat -> {
            router.backToRoot()
            dependencies.bottomTabController.change(CommonBottomBarTabType.Chat)
          }
          is ProfileApplicationEffect.ShowError -> {
            snackbarHostState.showError(effect.error)
          }
        }
      }
    }

    BackHandler { eventReceiver.invoke(Ui.Click.Back) }

    Scaffold(
      topBar = { Toolbar(onBackClick = { eventReceiver.invoke(Ui.Click.Back) }, title = "Заявка") },
      bottomBar = {
        if (isChatSupport) {
          BottomBarStack {
            FButton(
              text = "Написать",
              isLoading = state.isCreatingChatLoading,
            ) {
              eventReceiver(Ui.Click.Chat)
            }
          }
        }
      }
    ) { contentPadding ->
      val application = state.playerApplication

      val positionTitles =
        remember(application.footballPosition) { application.footballPosition.mapToTitle() }

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
            error = painterResource(drawable.ic_60_profile_placeholder),
          )
          Spacer(modifier = Modifier.height(20.dp))
        }
        item {
          Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = application.name.orEmpty(),
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
                      ),
                  )
                }
              }
            }
            Spacer(modifier = Modifier.height(12.dp))
          }
        }
        if (application.preferredTournaments.isNotEmpty()) {
          item {
            SectionCard(modifier = Modifier.padding(horizontal = 16.dp), title = "Турниры") {
              SectionText(value = application.preferredTournaments.joinTitleToString())
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
        if (application.tournamentsExperience?.isNotBlank() == true) {
          item {
            SectionCard(
              modifier = Modifier.padding(horizontal = 16.dp),
              title = "Турнирный опыт",
            ) {
              SectionText(value = application.tournamentsExperience.orEmpty())
            }
          }
        }
        item { Spacer(modifier = Modifier.height(20.dp)) }
        if (application.footballExperience?.isNotBlank() == true) {
          item {
            SectionCard(
              modifier = Modifier.padding(horizontal = 16.dp),
              title = "Футбольный опыт",
            ) {
              SectionText(value = application.footballExperience.orEmpty())
            }
          }
        }
        item { Spacer(modifier = Modifier.height(20.dp)) }
        if (application.contact?.isNotBlank() == true) {
          item {
            SectionCard(
              modifier = Modifier.padding(horizontal = 16.dp),
              title = "Контактная информация",
            ) {
              SectionText(value = application.contact.orEmpty())
            }
          }
        }
        item { Spacer(modifier = Modifier.height(20.dp)) }
      }
    }
  }
}
