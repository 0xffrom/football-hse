package andryuh.football.feature_chat.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.core_elmslie.StoreFactoryKey
import andryuh.football.domain_chat.ChatFeatureApi
import andryuh.football.feature_chat.feature_api.ChatFeatureApiImpl
import andryuh.football.feature_chat.screens.chat.presentation.ChatStoreFactory

@Module
abstract class ChatFeatureModule {

  @Binds internal abstract fun ChatFeatureApiImpl.bindChatFeatureApi(): ChatFeatureApi

  @Binds
  @IntoMap
  @StoreFactoryKey(ChatStoreFactory::class)
  internal abstract fun ChatStoreFactory.bindChatStoreFactory(): StoreFactory
}
