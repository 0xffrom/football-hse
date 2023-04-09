package goshka133.football.feature_auth.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import goshka133.football.core_auth.feature_api.RefreshSessionFeatureApi
import goshka133.football.core_elmslie.StoreFactory
import goshka133.football.core_elmslie.StoreFactoryKey
import goshka133.football.domain_auth.AuthDomainModule
import goshka133.football.domain_auth.AuthFeatureApi
import goshka133.football.feature_auth.feature_api.AuthFeatureApiImpl
import goshka133.football.feature_auth.feature_api.RefreshSessionFeatureApiImpl
import goshka133.football.feature_auth.screens.auth.presentation.AuthStoreFactory
import goshka133.football.feature_auth.screens.origination.presentation.OriginationStoreFactory

@Module(includes = [AuthDomainModule::class])
abstract class AuthFeatureModule {

  @Binds internal abstract fun AuthFeatureApiImpl.bindAuthFeatureApi(): AuthFeatureApi

  @Binds
  internal abstract fun RefreshSessionFeatureApiImpl.bindRefreshSessionFeatureApi():
    RefreshSessionFeatureApi

  @Binds
  @IntoMap
  @StoreFactoryKey(AuthStoreFactory::class)
  internal abstract fun AuthStoreFactory.bindAuthStoreFactory(): StoreFactory

  @Binds
  @IntoMap
  @StoreFactoryKey(OriginationStoreFactory::class)
  internal abstract fun OriginationStoreFactory.bindOriginationStoreFactory(): StoreFactory
}
