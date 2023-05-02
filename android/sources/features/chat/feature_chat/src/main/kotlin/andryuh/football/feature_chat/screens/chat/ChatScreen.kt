package andryuh.football.feature_chat.screens.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import andryuh.football.core_elmslie.rememberEventReceiver
import andryuh.football.core_elmslie.rememberStore
import andryuh.football.core_kotlin.Resource
import andryuh.football.core_navigation.LocalRouter
import andryuh.football.feature_chat.R
import andryuh.football.feature_chat.screens.chat.presentation.ChatEffect
import andryuh.football.feature_chat.screens.chat.presentation.ChatEvent
import andryuh.football.feature_chat.screens.chat.presentation.ChatStoreFactory
import andryuh.football.feature_chat.screens.chat.ui.ConversationCard
import andryuh.football.feature_chat.screens.chat.ui.ConversationCardShimmer
import andryuh.football.feature_chat.screens.conversation.ConversationScreen
import andryuh.football.ui_kit.BaseScreen
import andryuh.football.ui_kit.theme.FootballColors
import andryuh.football.ui_kit.theme.Style16400
import andryuh.football.ui_kit.toolbar.Toolbar
import com.github.terrakok.modo.stack.forward
import kotlinx.parcelize.Parcelize

@Parcelize
internal class ChatScreen : BaseScreen() {

  @Composable
  override fun Content() {
    val store =
      rememberStore(
        storeFactoryClass = ChatStoreFactory::class.java,
        storeProvider = { storeFactory, _ -> storeFactory.create() },
        savedStateRegistryOwner = LocalSavedStateRegistryOwner.current,
        vmStoreOwner = LocalViewModelStoreOwner.current!!,
      )
    val eventReceiver = store.rememberEventReceiver()
    val state by store.states().collectAsState(store.currentState)

    val router = LocalRouter.current
    LaunchedEffect(key1 = store) {
      store.effects().collect { effect ->
        when (effect) {
          is ChatEffect.OpenConversation -> {
            router.forward(ConversationScreen(conversation = effect.conversation))
          }
        }
      }
    }
    Scaffold(
      topBar = { Toolbar(title = "Чат") },
    ) { contentPadding ->
      if (state.conversations.value?.isEmpty() == true) {
        Column(
          modifier = Modifier.fillMaxSize(),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          Image(
            modifier = Modifier.size(250.dp, 200.dp),
            painter = painterResource(id = R.drawable.img_empty_conversations),
            contentDescription = null
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "У Вас пока нет активных чатов",
            style = Style16400,
            color = FootballColors.Text.Secondary,
          )
        }
      } else {
        LazyColumn(
          modifier = Modifier.fillMaxSize(),
          contentPadding = contentPadding,
        ) {
          when (val conversationResource = state.conversations) {
            is Resource.Data -> {
              items(conversationResource.data) { conversation ->
                ConversationCard(
                  conversation = conversation,
                  onClick = {
                    eventReceiver.invoke(ChatEvent.Ui.Click.ConversationCard(conversation))
                  },
                )
              }
            }
            else -> {
              items(5) { ConversationCardShimmer() }
            }
          }
        }
      }
    }
  }
}
