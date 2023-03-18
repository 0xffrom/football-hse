package goshka133.football.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import goshka133.football.core_elmslie.di.ElmDependencies
import goshka133.football.feature_auth.di.AuthFeatureDependencies
import goshka133.football.feature_auth.di.AuthFeatureModule
import goshka133.football.feature_chat.di.ChatFeatureDependencies
import goshka133.football.feature_chat.di.ChatFeatureModule
import goshka133.football.feature_main.di.MainFeatureDependencies
import goshka133.football.feature_main.di.MainFeatureModule
import goshka133.football.feature_profile.di.ProfileFeatureDependencies
import goshka133.football.feature_profile.di.ProfileFeatureModule
import goshka133.football.feature_search.di.SearchFeatureDependencies
import goshka133.football.feature_search.di.SearchFeatureModule
import javax.inject.Singleton

@Component(
  modules =
    [
      CoreModule::class,
      AuthFeatureModule::class,
      MainFeatureModule::class,
      ChatFeatureModule::class,
      SearchFeatureModule::class,
      ProfileFeatureModule::class,
    ]
)
@Singleton
interface ApplicationComponent :
  RootDependencies,
  ElmDependencies,
  AuthFeatureDependencies,
  ChatFeatureDependencies,
  SearchFeatureDependencies,
  ProfileFeatureDependencies,
  MainFeatureDependencies {

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance applicationContext: Context): ApplicationComponent
  }
}
