package goshka133.football.feature_chat.di

import dagger.Module
import goshka133.football.domain_chat.ChatFeatureApi
import goshka133.football.feature_chat.feature_api.ChatFeatureApiImpl

@Module
abstract class ChatModule {

  internal abstract fun ChatFeatureApiImpl.bindChatFeatureApi(): ChatFeatureApi
}
