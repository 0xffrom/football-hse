package andryuh.football.feature_profile.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.core_elmslie.StoreFactoryKey
import andryuh.football.domain_profile.ProfileDomainModule
import andryuh.football.domain_profile.ProfileFeatureApi
import andryuh.football.feature_profile.feature_api.ProfileFeatureApiImpl
import andryuh.football.feature_profile.screens.edit_profile.presentation.EditProfileStoreFactory
import andryuh.football.feature_profile.screens.profile.presentation.ProfileStoreFactory
import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationStoreFactory

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
