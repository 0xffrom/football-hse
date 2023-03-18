package goshka133.football.feature_chat.feature_api

import com.github.terrakok.modo.Screen
import goshka133.football.domain_chat.ChatFeatureApi
import goshka133.football.feature_chat.screens.chat.ChatScreen
import javax.inject.Inject

internal class ChatFeatureApiImpl @Inject constructor() : ChatFeatureApi {

  override fun getScreen(): Screen = ChatScreen()
}
