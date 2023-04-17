package andryuh.football.feature_profile.di

import andryuh.football.core_elmslie.StoreFactory
import andryuh.football.core_elmslie.StoreFactoryKey
import andryuh.football.domain_profile.ProfileApi
import andryuh.football.domain_profile.ProfileFeatureApi
import andryuh.football.feature_profile.feature_api.ProfileFeatureApiImpl
import andryuh.football.feature_profile.screens.edit_profile.presentation.EditProfileStoreFactory
import andryuh.football.feature_profile.screens.profile.presentation.ProfileStoreFactory
import andryuh.football.feature_profile.screens.team_registration.presentation.TeamRegistrationStoreFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import retrofit2.Retrofit

@Module(includes = [ProfileProvidesModule::class])
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

@Module
internal object ProfileProvidesModule {

  @Provides
  @Singleton
  fun provideProfileApi(retrofit: Retrofit): ProfileApi {
    return retrofit.create(ProfileApi::class.java)
  }
}
