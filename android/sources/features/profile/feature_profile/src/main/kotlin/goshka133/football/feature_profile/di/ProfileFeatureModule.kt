package goshka133.football.feature_profile.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import goshka133.football.core_elmslie.StoreFactory
import goshka133.football.core_elmslie.StoreFactoryKey
import goshka133.football.domain_profile.ProfileDomainModule
import goshka133.football.domain_profile.ProfileFeatureApi
import goshka133.football.feature_profile.feature_api.ProfileFeatureApiImpl
import goshka133.football.feature_profile.screens.edit_profile.presentation.EditProfileStoreFactory
import goshka133.football.feature_profile.screens.profile.presentation.ProfileStoreFactory
import goshka133.football.feature_profile.screens.team_registration.presentation.TeamRegistrationStoreFactory

@Module(includes = [ProfileDomainModule::class])
abstract class ProfileFeatureModule {

  @Binds internal abstract fun ProfileFeatureApiImpl.bindProfileFeatureApi(): ProfileFeatureApi

  @Binds
  @IntoMap
  @StoreFactoryKey(ProfileStoreFactory::class)
  internal abstract fun ProfileStoreFactory.bindProfileStoreFactory(): StoreFactory

  @Binds
  @IntoMap
  @StoreFactoryKey(TeamRegistrationStoreFactory::class)
  internal abstract fun TeamRegistrationStoreFactory.bindTeamRegistrationStoreFactory():
    StoreFactory

  @Binds
  @IntoMap
  @StoreFactoryKey(EditProfileStoreFactory::class)
  internal abstract fun EditProfileStoreFactory.bindEditProfileStoreFactory(): StoreFactory
}
