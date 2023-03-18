package goshka133.football.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import goshka133.football.core_elmslie.di.ElmDependencies
import goshka133.football.feature_auth.di.AuthFeatureModule
import goshka133.football.feature_main.di.MainFeatureModule
import javax.inject.Singleton

@Component(
  modules =
    [
      CoreModule::class,
      AuthFeatureModule::class,
      MainFeatureModule::class,

    ]
)
@Singleton
interface ApplicationComponent : RootDependencies, ElmDependencies {

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance applicationContext: Context): ApplicationComponent
  }
}
