package andryuh.football.feature_chat.di

import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.core_elmslie.StoreFactoryKey
import andryuh.football.domain_chat.ChatApi
import andryuh.football.domain_chat.ChatFeatureApi
import andryuh.football.feature_chat.feature_api.ChatFeatureApiImpl
import andryuh.football.feature_chat.screens.chat.presentation.ChatStoreFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import retrofit2.Retrofit

@Module(includes = [ChatFeatureProvidersModule::class])
abstract class ChatFeatureModule {

  @Binds internal abstract fun ChatFeatureApiImpl.bindChatFeatureApi(): ChatFeatureApi

  @Binds
  @IntoMap
  @StoreFactoryKey(ChatStoreFactory::class)
  internal abstract fun ChatStoreFactory.bindChatStoreFactory(): StoreFactory
}

@Module
object ChatFeatureProvidersModule {

  @Provides
  @Singleton
  fun provideChatApi(retrofit: Retrofit): ChatApi {
    return retrofit.create(ChatApi::class.java)
  }
}
