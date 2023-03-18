package goshka133.football.feature_profile.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import goshka133.football.core_elmslie.StoreFactory
import goshka133.football.core_elmslie.StoreFactoryKey
import goshka133.football.domain_profile.ProfileFeatureApi
import goshka133.football.feature_profile.feature_api.ProfileFeatureApiImpl
import goshka133.football.feature_profile.screens.profile.presentation.ProfileStoreFactory

@Module
abstract class ProfileFeatureModule {

  @Binds internal abstract fun ProfileFeatureApiImpl.bindProfileFeatureApi(): ProfileFeatureApi

  @Binds
  @IntoMap
  @StoreFactoryKey(ProfileStoreFactory::class)
  internal abstract fun ProfileStoreFactory.bindProfileStoreFactory(): StoreFactory
}
