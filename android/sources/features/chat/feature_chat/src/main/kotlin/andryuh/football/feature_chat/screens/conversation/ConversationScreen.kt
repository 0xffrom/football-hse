package andryuh.football.feature_chat.screens.conversation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import andryuh.football.core_elmslie.rememberEventReceiver
import andryuh.football.core_elmslie.rememberStore
import andryuh.football.core_navigation.LocalRouter
import andryuh.football.domain_chat.dto.Conversation
import andryuh.football.feature_chat.screens.conversation.presentation.ConversationEffect
import andryuh.football.feature_chat.R as chat_R
import andryuh.football.feature_chat.screens.conversation.presentation.ConversationEvent
import andryuh.football.feature_chat.screens.conversation.presentation.ConversationStoreFactory
import andryuh.football.feature_chat.screens.conversation.ui.MessageGroupBubble
import andryuh.football.ui_kit.snack_bar.LocalSnackBarHostState
import andryuh.football.ui_kit.snack_bar.showError
import andryuh.football.ui_kit.R as ui_kit_R
import andryuh.football.ui_kit.text_field.FTextField
import andryuh.football.ui_kit.theme.FootballColors
import andryuh.football.ui_kit.toolbar.Toolbar
import coil.compose.AsyncImage
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.back
import kotlinx.parcelize.Parcelize

@Parcelize
internal class ConversationScreen(
  private val conversation: Conversation,
) : Screen(generateScreenKey()) {

  @Composable
  override fun Content() {
    val store =
      rememberStore(
        storeFactoryClass = ConversationStoreFactory::class.java,
        storeProvider = { storeFactory, _ -> storeFactory.create(conversation) },
      )
    val state by store.states().collectAsState(store.currentState)
    val eventReceiver = store.rememberEventReceiver()
    val snackbarHostState = LocalSnackBarHostState.current
    val router = LocalRouter.current

    LaunchedEffect(Unit) {
      store.effects().collect { effect ->
        when (effect) {
          is ConversationEffect.ShowError -> {
            snackbarHostState.showError(effect.error)
          }
          is ConversationEffect.Close -> {
            router.back()
          }
        }
      }
    }

    Scaffold(
      modifier = Modifier.fillMaxSize(),
      topBar = {
        Toolbar(
          title = state.conversation.name,
          onBackClick = { eventReceiver.invoke(ConversationEvent.Ui.Click.Back) },
          rightAccessor = {
            AsyncImage(
              modifier = Modifier.size(34.dp).clip(CircleShape),
              model = state.conversation.photoUrl,
              contentDescription = null,
              contentScale = ContentScale.Crop,
              error = painterResource(ui_kit_R.drawable.ic_60_profile_placeholder)
            )
          }
        )
      },
      bottomBar = {
        Row(
          modifier =
            Modifier.background(Color.White)
              .navigationBarsPadding()
              .imePadding()
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 8.dp),
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          FTextField(
            modifier = Modifier.weight(1f),
            value = state.messageTextFieldValue,
            onValueChange = { value ->
              eventReceiver.invoke(ConversationEvent.Ui.Action.OnMessageTextFieldChange(value))
            },
            placeholder = "Сообщение",
          )
          IconButton(
            modifier = Modifier.size(24.dp),
            onClick = { eventReceiver.invoke(ConversationEvent.Ui.Click.SendMessage) }
          ) {
            Icon(
              modifier = Modifier.size(24.dp),
              painter = painterResource(id = chat_R.drawable.ic_24_send_message),
              contentDescription = null,
              tint = FootballColors.Primary,
            )
          }
        }
        Spacer(
          modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.Gray.copy(alpha = 0.5f))
        )
      }
    ) { contentPadding ->
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding,
        reverseLayout = true,
      ) {
        items(state.messageGroups) { messageGroup ->
          Spacer(modifier = Modifier.height(8.dp))
          MessageGroupBubble(
            modifier = Modifier.padding(horizontal = 16.dp),
            messageGroup = messageGroup,
          )
        }
      }
    }
  }
}
