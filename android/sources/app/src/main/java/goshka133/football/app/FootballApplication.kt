package goshka133.football.app

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.request.CachePolicy
import coil.size.Precision
import goshka133.football.core_di.DependenciesProvider
import goshka133.football.di.ApplicationComponent
import goshka133.football.di.DaggerApplicationComponent

internal class FootballApplication : Application(), DependenciesProvider {

  private lateinit var applicationComponent: ApplicationComponent

  override fun onCreate() {
    super.onCreate()

    applicationComponent =
      DaggerApplicationComponent.factory().create(applicationContext = applicationContext)

    Coil.setImageLoader {
      ImageLoader.Builder(applicationContext)
        .diskCachePolicy(CachePolicy.ENABLED)
        .networkCachePolicy(CachePolicy.ENABLED)
        .precision(Precision.AUTOMATIC)
        .build()
    }
  }

  override fun <T> provide(): T {
    @Suppress("UNCHECKED_CAST") return applicationComponent as T
  }
}
