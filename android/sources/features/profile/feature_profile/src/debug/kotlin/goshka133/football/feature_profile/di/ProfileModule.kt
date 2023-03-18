package goshka133.football.feature_profile.di

import dagger.Module
import goshka133.football.domain_profile.ProfileFeatureApi
import goshka133.football.feature_profile.feature_api.ProfileFeatureApiImpl

@Module
abstract class ProfileModule {

  internal abstract fun ProfileFeatureApiImpl.bindProfileFeatureApi(): ProfileFeatureApi
}
