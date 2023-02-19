package goshka133.football.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import goshka133.football.feature_auth.di.AuthFeatureModule

@Component(
  modules =
  [
    CoreModule::class,
    AuthFeatureModule::class,
  ]
)
interface ApplicationComponent : MainDependencies {

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance applicationContext: Context): ApplicationComponent
  }
}
