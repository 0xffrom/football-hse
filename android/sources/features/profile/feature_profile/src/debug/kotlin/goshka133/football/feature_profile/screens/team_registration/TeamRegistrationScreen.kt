package goshka133.football.feature_profile.screens.team_registration

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.github.terrakok.modo.stack.back
import goshka133.football.core_elmslie.EventReceiver
import goshka133.football.core_elmslie.rememberEventReceiver
import goshka133.football.core_elmslie.rememberStore
import goshka133.football.core_navigation.LocalRouter
import goshka133.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEffect
import goshka133.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEvent
import goshka133.football.feature_profile.screens.team_registration.presentation.TeamRegistrationEvent.Ui.Click
import goshka133.football.feature_profile.screens.team_registration.presentation.TeamRegistrationState
import goshka133.football.feature_profile.screens.team_registration.presentation.TeamRegistrationStoreFactory
import goshka133.football.ui_kit.BaseScreen
import goshka133.football.ui_kit.button.BottomBarStack
import goshka133.football.ui_kit.button.FButton
import goshka133.football.ui_kit.text_field.FTextField
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

    val photoPickerContract =
      rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri
        ->
        eventReceiver.invoke(TeamRegistrationEvent.Ui.Action.OnImageReceived(uri))
      }

    LaunchedEffect(key1 = store) {
      store.effects().collect { effect ->
        when (effect) {
          is TeamRegistrationEffect.Close -> router.back()
          is TeamRegistrationEffect.OpenPhotoPicker -> {
            photoPickerContract.launch(
              PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
          }
        }
      }
    }

    Scaffold(
      topBar = {
        Box(
          modifier = Modifier.systemBarsPadding().heightIn(min = 44.dp).padding(horizontal = 16.dp),
          contentAlignment = Alignment.CenterStart,
        ) {
          IconButton(onClick = { eventReceiver.invoke(Click.Back) }) {
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
            eventReceiver = eventReceiver,
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
              eventReceiver.invoke(TeamRegistrationEvent.Ui.Action.OnCaptainNameTextFieldChange(it))
            },
            title = "Капитан команды",
            placeholder = "Введите имя капитана команды",
          )
          Spacer(modifier = Modifier.height(24.dp))
        }
      }
    }
  }

  @Composable
  private fun AvatarBox(
    state: TeamRegistrationState,
    eventReceiver: EventReceiver<TeamRegistrationEvent>,
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
        Box(
          modifier =
            Modifier.size(size.value)
              .clip(CircleShape)
              .background(color = FootballColors.Surface2, shape = CircleShape)
              .clickable { eventReceiver.invoke(Click.Avatar) },
          contentAlignment = Alignment.Center,
        ) {
          Icon(
            painter = painterResource(id = goshka133.football.ui_kit.R.drawable.ic_24_plus),
            contentDescription = null,
            tint = FootballColors.Primary,
          )
        }
      } else {
        Image(
          modifier =
            Modifier.size(size.value)
              .clip(CircleShape)
              .border(width = 1.dp, color = FootballColors.Primary, shape = CircleShape)
              .clickable { eventReceiver.invoke(Click.Avatar) },
          painter =
            rememberAsyncImagePainter(model = state.photoUri, contentScale = ContentScale.Crop),
          contentDescription = null,
        )
      }
    }
  }

  @Composable
  private fun FormField(
    textFieldValue: TextFieldValue,
    onValueChange: (value: TextFieldValue) -> Unit,
    title: String,
    placeholder: String,
    modifier: Modifier = Modifier,
  ) {
    Column(
      modifier = modifier,
      verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = remember(title) { title.uppercase() },
        textAlign = TextAlign.Start,
        color = FootballColors.Text.Secondary,
        style =
          TextStyle(
            fontWeight = FontWeight.W500,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp,
          ),
      )
      FTextField(
        modifier = Modifier.fillMaxWidth(),
        value = textFieldValue,
        onValueChange = onValueChange,
        placeholder = placeholder,
      )
    }
  }
}
