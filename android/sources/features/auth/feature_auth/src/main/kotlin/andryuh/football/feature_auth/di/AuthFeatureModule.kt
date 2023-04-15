package andryuh.football.feature_auth.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import andryuh.football.core_auth.feature_api.RefreshSessionFeatureApi
import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.core_elmslie.StoreFactoryKey
import andryuh.football.domain_auth.AuthDomainModule
import andryuh.football.domain_auth.AuthFeatureApi
import andryuh.football.feature_auth.feature_api.AuthFeatureApiImpl
import andryuh.football.feature_auth.feature_api.RefreshSessionFeatureApiImpl
import andryuh.football.feature_auth.screens.auth.presentation.AuthStoreFactory
import andryuh.football.feature_auth.screens.origination.presentation.OriginationStoreFactory

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
