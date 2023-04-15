package andryuh.football.feature_profile.screens.team_registration

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.github.terrakok.modo.stack.back
import andryuh.football.core_elmslie.rememberEventReceiver
import andryuh.football.core_elmslie.rememberStore
import andryuh.football.core_navigation.LocalRouter
import andryuh.football.domain_profile.dto.Profile
import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEffect
import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEvent
import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEvent.Ui.Click
import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationState
import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationStoreFactory
import andryuh.football.feature_profile.ui.*
import andryuh.football.feature_profile.ui.AvatarPickerSheetContent
import andryuh.football.feature_profile.ui.DefaultSheetBackgroundColor
import andryuh.football.feature_profile.ui.DefaultSheetShape
import andryuh.football.feature_profile.ui.FormField
import andryuh.football.ui_kit.BaseScreen
import andryuh.football.ui_kit.button.BottomBarStack
import andryuh.football.ui_kit.button.FButton
import andryuh.football.ui_kit.theme.FootballColors
import andryuh.football.ui_kit.toolbar.Toolbar
import kotlinx.parcelize.Parcelize

@Parcelize
internal class TeamRegistrationScreen(
  private val profile: Profile,
) : BaseScreen() {

  @OptIn(ExperimentalMaterialApi::class)
  @Composable
  override fun Content() {
    val store =
      rememberStore(
        storeFactoryClass = TeamRegistrationStoreFactory::class.java,
        storeProvider = { storeFactory, _ -> storeFactory.create(profile = profile) },
      )

    val state by store.states().collectAsState(store.currentState)
    val eventReceiver = store.rememberEventReceiver()
    val router = LocalRouter.current

    val photoPickerContract =
      rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri
        ->
        eventReceiver.invoke(TeamRegistrationEvent.Ui.Action.OnImageReceived(uri))
      }

    val sheetState =
      rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
      )

    BackHandler { eventReceiver.invoke(Click.Back) }

    LaunchedEffect(key1 = store) {
      store.effects().collect { effect ->
        when (effect) {
          is TeamRegistrationEffect.Close -> router.back()
          is TeamRegistrationEffect.OpenPhotoPicker -> {
            photoPickerContract.launch(
              PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
          }
          is TeamRegistrationEffect.ShowBottomPhotoPickerSheet -> {
            sheetState.show()
          }
          is TeamRegistrationEffect.HideBottomPhotoPickerSheet -> {
            sheetState.hide()
          }
        }
      }
    }

    ModalBottomSheetLayout(
      sheetState = sheetState,
      sheetShape = DefaultSheetShape,
      sheetBackgroundColor = DefaultSheetBackgroundColor,
      sheetContent = {
        AvatarPickerSheetContent(
          title = "Загрузите логотип Вашей команды",
          onCloseClick = { eventReceiver.invoke(Click.PhotoPickerSheetClose) },
          onContinueClick = { eventReceiver.invoke(Click.PhotoPickerSheetContinue) },
        )
      }
    ) {
      Scaffold(
        topBar = {
          Toolbar(
            onBackClick = { eventReceiver.invoke(Click.Back) },
            title = "Регистрация команды",
          )
        },
        bottomBar = {
          BottomBarStack {
            FButton(
              text = "Зарегистрировать",
              isLoading = state.isLoading,
            ) {
              eventReceiver(Click.Continue)
            }
          }
        }
      ) { contentPadding ->
        LazyColumn(contentPadding = contentPadding) {
          item {
            AvatarBox(
              state = state,
              onClick = { eventReceiver.invoke(Click.Avatar) },
            )
            Spacer(modifier = Modifier.height(36.dp))
          }
          item {
            FormField(
              modifier = Modifier.padding(horizontal = 16.dp),
              textFieldValue = state.teamNameTextField,
              onValueChange = {
                eventReceiver.invoke(TeamRegistrationEvent.Ui.Action.OnTeamNameTextFieldChange(it))
              },
              title = "Название команды",
              placeholder = "Введите название",
            )
            Spacer(modifier = Modifier.height(24.dp))
          }
          item {
            FormField(
              modifier = Modifier.padding(horizontal = 16.dp),
              textFieldValue = state.teamInfoTextField,
              onValueChange = {
                eventReceiver.invoke(TeamRegistrationEvent.Ui.Action.OnTeamInfoTextFieldChange(it))
              },
              title = "Информация о команде",
              placeholder = "Расскажите в каких лигах принимает участие Ваша команда",
            )
            Spacer(modifier = Modifier.height(24.dp))
          }
          item {
            FormField(
              modifier = Modifier.padding(horizontal = 16.dp),
              textFieldValue = state.captainNameTextField,
              onValueChange = {
                eventReceiver.invoke(
                  TeamRegistrationEvent.Ui.Action.OnCaptainNameTextFieldChange(it)
                )
              },
              title = "Капитан команды",
              placeholder = "Введите имя капитана команды",
            )
            Spacer(modifier = Modifier.height(24.dp))
          }
        }
      }
    }
  }

  @Composable
  private fun AvatarBox(
    state: TeamRegistrationState,
    onClick: () -> Unit,
  ) {
    val size =
      animateDpAsState(
        targetValue = if (state.photoUri == null) 70.dp else 140.dp,
        animationSpec = tween(durationMillis = 1500),
      )

    Box(
      modifier = Modifier.fillMaxWidth(),
      contentAlignment = Alignment.Center,
    ) {
      if (state.photoUri == null) {
        AvatarNewPhotoBox(
          size = size.value,
          onClick = onClick,
        )
      } else {
        Image(
          modifier =
            Modifier.size(size.value)
              .clip(CircleShape)
              .border(width = 1.dp, color = FootballColors.Primary, shape = CircleShape)
              .clickable(onClick = onClick),
          painter =
            rememberAsyncImagePainter(model = state.photoUri, contentScale = ContentScale.Crop),
          contentDescription = null,
        )
      }
    }
  }
}
