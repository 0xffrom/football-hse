package andryuh.football.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import andryuh.football.core_elmslie.di.ElmDependencies
import andryuh.football.feature_auth.di.AuthFeatureDependencies
import andryuh.football.feature_auth.di.AuthFeatureModule
import andryuh.football.feature_chat.di.ChatFeatureDependencies
import andryuh.football.feature_chat.di.ChatFeatureModule
import andryuh.football.feature_main.di.MainFeatureDependencies
import andryuh.football.feature_main.di.MainFeatureModule
import andryuh.football.feature_profile.di.ProfileFeatureDependencies
import andryuh.football.feature_profile.di.ProfileFeatureModule
import andryuh.football.feature_search.di.SearchFeatureDependencies
import andryuh.football.feature_search.di.SearchFeatureModule
import andryuh.football.feature_team.di.TeamFeatureDependencies
import andryuh.football.feature_team.di.TeamFeatureModule
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
      TeamFeatureModule::class,
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
  MainFeatureDependencies,
  TeamFeatureDependencies {

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance applicationContext: Context): ApplicationComponent
  }
}
