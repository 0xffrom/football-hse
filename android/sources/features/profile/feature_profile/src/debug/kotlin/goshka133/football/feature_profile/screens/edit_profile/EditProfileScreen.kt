package goshka133.football.feature_profile.screens.edit_profile

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
import goshka133.football.core_elmslie.rememberEventReceiver
import goshka133.football.core_elmslie.rememberStore
import goshka133.football.core_navigation.LocalRouter
import goshka133.football.domain_profile.dto.Profile
import goshka133.football.feature_profile.screens.edit_profile.presentation.EditProfileEffect
import goshka133.football.feature_profile.screens.edit_profile.presentation.EditProfileEvent.Ui.Action
import goshka133.football.feature_profile.screens.edit_profile.presentation.EditProfileEvent.Ui.Click
import goshka133.football.feature_profile.screens.edit_profile.presentation.EditProfileState
import goshka133.football.feature_profile.screens.edit_profile.presentation.EditProfileStoreFactory
import goshka133.football.feature_profile.ui.*
import goshka133.football.feature_profile.ui.AvatarNewPhotoBox
import goshka133.football.feature_profile.ui.AvatarPickerSheetContent
import goshka133.football.feature_profile.ui.DefaultSheetBackgroundColor
import goshka133.football.feature_profile.ui.DefaultSheetShape
import goshka133.football.feature_profile.ui.FormField
import goshka133.football.ui_kit.BaseScreen
import goshka133.football.ui_kit.button.BottomBarStack
import goshka133.football.ui_kit.button.FButton
import goshka133.football.ui_kit.theme.FootballColors
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
        }
      }
    }

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
                eventReceiver.invoke(Action.OnTournamentsExperienceTextFieldChange(it))
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
                eventReceiver.invoke(Action.OnTournamentsExperienceTextFieldChange(it))
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
      Image(
        modifier =
          Modifier.size(size.value)
            .clip(CircleShape)
            .border(width = 1.dp, color = FootballColors.Primary, shape = CircleShape)
            .clickable(onClick = onClick),
        painter =
          rememberAsyncImagePainter(
            model =
              if (state.profile.imageUrl.isNullOrBlank()) {
                state.loadedImageUri
              } else {
                state.profile.imageUrl
              },
            contentScale = ContentScale.Crop
          ),
        contentDescription = null,
      )
    }
  }
}