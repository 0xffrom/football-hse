package andryuh.football.core_elmslie

import dagger.MapKey
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

interface StoreFactory

@MapKey
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class StoreFactoryKey(val value: KClass<out StoreFactory>)

@Singleton
class StoreFactoryProvider
@Inject
constructor(
  private val providers: Map<Class<out StoreFactory>, @JvmSuppressWildcards Provider<StoreFactory>>,
) {

  fun <T : StoreFactory> get(storeFactoryClass: Class<T>): T {
    val creatorProvider = providers[storeFactoryClass]
    val creator = requireNotNull(creatorProvider).get()

    @Suppress("UNCHECKED_CAST") return creator as T
  }
}
