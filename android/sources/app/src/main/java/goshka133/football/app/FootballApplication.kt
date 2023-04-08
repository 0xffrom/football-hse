package goshka133.football.app

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.request.CachePolicy
import coil.size.Precision
import goshka133.football.core_di.DependenciesProvider
import goshka133.football.di.ApplicationComponent
import goshka133.football.di.DaggerApplicationComponent
import timber.log.Timber
import vivid.money.elmslie.android.logger.strategy.Crash
import vivid.money.elmslie.core.config.ElmslieConfig
import vivid.money.elmslie.core.logger.strategy.LogStrategy

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

    Timber.plant(Timber.DebugTree())

    ElmslieConfig.logger {
      fatal(Crash)
      nonfatal(TimberLog.E)
      debug(TimberLog.D)
    }
  }

  override fun <T> provide(): T {
    @Suppress("UNCHECKED_CAST") return applicationComponent as T
  }
}

internal object TimberLog {

  val E = timberLogger(Timber::e)
  val D = timberLogger(Timber::d)

  private fun timberLogger(log: (Throwable?, String) -> Unit) = LogStrategy { _, message, error ->
    log(error, message)
  }
}
