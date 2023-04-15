package andryuh.football.feature_chat.screens.chat

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import andryuh.football.core_elmslie.rememberStore
import andryuh.football.feature_chat.screens.chat.presentation.ChatStoreFactory
import andryuh.football.ui_kit.BaseScreen
import kotlinx.parcelize.Parcelize

@Parcelize
internal class ChatScreen : BaseScreen() {

  @Composable
  override fun Content() {
    val store =
      rememberStore(
        storeFactoryClass = ChatStoreFactory::class.java,
        storeProvider = { storeFactory, _ -> storeFactory.create() },
      )
    val state by store.states().collectAsState(store.currentState)

    LaunchedEffect(key1 = store) {
      store.effects().collect { effect ->
        when (effect) {
          else -> Unit
        }
      }
    }
    Scaffold(
      topBar = {},
    ) { contentPadding ->
    }
  }
}
