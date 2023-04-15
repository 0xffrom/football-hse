package andryuh.football.feature_profile.screens.edit_profile

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
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
import coil.compose.AsyncImage
import com.github.terrakok.modo.stack.back
import andryuh.football.core_elmslie.rememberEventReceiver
import andryuh.football.core_elmslie.rememberStore
import andryuh.football.core_navigation.LocalRouter
import andryuh.football.domain_profile.dto.Profile
import andryuh.football.feature_profile.screens.edit_profile.presentation.EditProfileEffect
import andryuh.football.feature_profile.screens.edit_profile.presentation.EditProfileEvent.Ui.Action
import andryuh.football.feature_profile.screens.edit_profile.presentation.EditProfileEvent.Ui.Click
import andryuh.football.feature_profile.screens.edit_profile.presentation.EditProfileState
import andryuh.football.feature_profile.screens.edit_profile.presentation.EditProfileStoreFactory
import andryuh.football.feature_profile.ui.*
import andryuh.football.feature_profile.ui.AvatarNewPhotoBox
import andryuh.football.feature_profile.ui.AvatarPickerSheetContent
import andryuh.football.feature_profile.ui.DefaultSheetBackgroundColor
import andryuh.football.feature_profile.ui.DefaultSheetShape
import andryuh.football.feature_profile.ui.FormField
import andryuh.football.ui_kit.BaseScreen
import andryuh.football.ui_kit.button.BottomBarStack
import andryuh.football.ui_kit.button.FButton
import andryuh.football.ui_kit.snack_bar.LocalSnackBarHostState
import andryuh.football.ui_kit.theme.FootballColors
import andryuh.football.ui_kit.toolbar.Toolbar
import kotlinx.parcelize.Parcelize

@Parcelize
internal class EditProfileScreen(
  private val profile: Profile,
) : BaseScreen() {

  @OptIn(ExperimentalMaterialApi::class)
  @Composable
  override fun Content() {
    val store =
      rememberStore(
        storeFactoryClass = EditProfileStoreFactory::class.java,
        storeProvider = { storeFactory, _ ->
          storeFactory.create(
            profile = profile,
          )
        },
      )

    val state by store.states().collectAsState(store.currentState)
    val eventReceiver = store.rememberEventReceiver()
    val router = LocalRouter.current

    val photoPickerContract =
      rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri
        ->
        eventReceiver.invoke(Action.OnImageReceived(uri))
      }

    val sheetState =
      rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
      )

    val snackbarHostState = LocalSnackBarHostState.current

    LaunchedEffect(key1 = store) {
      store.effects().collect { effect ->
        when (effect) {
          is EditProfileEffect.Close -> router.back()
          is EditProfileEffect.OpenPhotoPicker -> {
            photoPickerContract.launch(
              PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
          }
          is EditProfileEffect.ShowBottomPhotoPickerSheet -> {
            sheetState.show()
          }
          is EditProfileEffect.HideBottomPhotoPickerSheet -> {
            sheetState.hide()
          }
          is EditProfileEffect.ShowError -> {
            effect.error.message?.let { message -> snackbarHostState.showSnackbar(message) }
          }
        }
      }
    }

    BackHandler { eventReceiver.invoke(Click.Back) }

    ModalBottomSheetLayout(
      sheetState = sheetState,
      sheetShape = DefaultSheetShape,
      sheetBackgroundColor = DefaultSheetBackgroundColor,
      sheetContent = {
        AvatarPickerSheetContent(
          title = "Загрузите аватар",
          onCloseClick = { eventReceiver.invoke(Click.PhotoPickerSheetClose) },
          onContinueClick = { eventReceiver.invoke(Click.PhotoPickerSheetContinue) },
        )
      }
    ) {
      Scaffold(
        topBar = {
          Toolbar(
            onBackClick = { eventReceiver.invoke(Click.Back) },
            title = "Редактирование профиля"
          )
        },
        bottomBar = {
          BottomBarStack {
            FButton(
              text = "Сохранить",
              isLoading = state.isLoading,
            ) {
              eventReceiver(Click.Continue)
            }
          }
        }
      ) { contentPadding ->
        LazyColumn(contentPadding = contentPadding) {
          item {
            AvatarBox(state = state, onClick = { eventReceiver.invoke(Click.Avatar) })
            Spacer(modifier = Modifier.height(36.dp))
          }
          item {
            FormField(
              modifier = Modifier.padding(horizontal = 16.dp),
              textFieldValue = state.fullNameTextFieldValue,
              onValueChange = { eventReceiver.invoke(Action.OnFullNameTextFieldChange(it)) },
              title = "ФИО",
              placeholder = "Введите Ваше ФИО",
            )
            Spacer(modifier = Modifier.height(24.dp))
          }
          item {
            FormField(
              modifier = Modifier.padding(horizontal = 16.dp),
              textFieldValue = state.footballExperienceTextFieldValue,
              onValueChange = {
                eventReceiver.invoke(Action.OnFootballExperienceTextFieldChange(it))
              },
              title = "Футбольный опыт",
              placeholder = "Расскажите про Ваш футбольный опыт",
            )
            Spacer(modifier = Modifier.height(24.dp))
          }
          item {
            FormField(
              modifier = Modifier.padding(horizontal = 16.dp),
              textFieldValue = state.tournamentsExperienceTextFieldValue,
              onValueChange = {
                eventReceiver.invoke(Action.OnTournamentsExperienceTextFieldChange(it))
              },
              title = "Опыт в турнирах НИУ ВШЭ",
              placeholder = "Расскажите в каких турнирах НИУ ВШЭ вы играли",
            )
            Spacer(modifier = Modifier.height(24.dp))
          }
          item {
            FormField(
              modifier = Modifier.padding(horizontal = 16.dp),
              textFieldValue = state.contactInfoTextFieldValue,
              onValueChange = {
                eventReceiver.invoke(Action.OnContactInfoTextFieldChange(it))
              },
              title = "Контактная информация",
              placeholder = "Телефон, Telegram...",
            )
            Spacer(modifier = Modifier.height(24.dp))
          }
          item {
            FormField(
              modifier = Modifier.padding(horizontal = 16.dp),
              textFieldValue = state.aboutInfoTextFieldValue,
              onValueChange = {
                eventReceiver.invoke(Action.OnAboutInfoTextFieldChange(it))
              },
              title = "О себе",
              placeholder = "Расскажите о своём опыте и достижениях",
            )
            Spacer(modifier = Modifier.height(24.dp))
          }
        }
      }
    }
  }
}

@Composable
private fun AvatarBox(
  state: EditProfileState,
  onClick: () -> Unit,
) {
  val isEmptyImage = state.profile.imageUrl.isNullOrBlank() && state.loadedImageUri == null
  val size =
    animateDpAsState(
      targetValue = if (isEmptyImage) 70.dp else 140.dp,
      animationSpec = tween(durationMillis = 1500),
    )

  Box(
    modifier = Modifier.fillMaxWidth(),
    contentAlignment = Alignment.Center,
  ) {
    if (isEmptyImage) {
      AvatarNewPhotoBox(
        size = size.value,
        onClick = onClick,
      )
    } else {
      AsyncImage(
        modifier =
          Modifier.size(size.value)
            .clip(CircleShape)
            .border(width = 1.dp, color = FootballColors.Primary, shape = CircleShape)
            .clickable(onClick = onClick),
        model =
          if (state.profile.imageUrl.isNullOrBlank()) {
            state.loadedImageUri
          } else {
            state.profile.imageUrl
          },
        contentDescription = null,
        contentScale = ContentScale.Crop,
      )
    }
  }
}
