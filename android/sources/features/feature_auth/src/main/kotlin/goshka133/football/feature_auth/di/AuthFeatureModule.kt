package goshka133.football.feature_auth.di

import dagger.Binds
import dagger.Module
import goshka133.football.domain_auth.AuthFeatureApi
import goshka133.football.feature_auth.feature_api.AuthFeatureApiImpl

@Module
abstract class AuthFeatureModule {

  @Binds
  internal abstract fun AuthFeatureApiImpl.bindAuthFeatureApi(): AuthFeatureApi
}
