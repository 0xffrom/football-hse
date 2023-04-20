package andryuh.football.core_elmslie

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryOwner
import andryuh.football.core_di.rememberDependencies
import andryuh.football.core_elmslie.di.ElmDependencies
import com.github.terrakok.modo.Screen
import vivid.money.elmslie.android.RetainedElmStore
import vivid.money.elmslie.android.RetainedElmStoreFactory
import vivid.money.elmslie.core.store.Store

@Composable
fun <SF : StoreFactory, Event : Any, Effect : Any, State : Any> Screen.rememberStore(
  storeFactoryClass: Class<SF>,
  key: String = storeFactoryClass.name,
  storeProvider: (SF, SavedStateHandle) -> Store<Event, Effect, State>,
  savedStateRegistryOwner: SavedStateRegistryOwner = this,
  vmStoreOwner: ViewModelStoreOwner = this,
): Store<Event, Effect, State> {
  val elmDependencies: ElmDependencies = rememberDependencies()
  val storeFactory = remember { elmDependencies.storeFactoryProvider.get(storeFactoryClass) }

  return remember(
    key1 = vmStoreOwner,
    key2 = savedStateRegistryOwner,
  ) {
    val factory =
      RetainedElmStoreFactory(
        stateRegistryOwner = savedStateRegistryOwner,
        storeFactory = { savedStateHandle -> storeProvider.invoke(storeFactory, savedStateHandle) },
        defaultArgs = Bundle(),
      )
    val provider = ViewModelProvider(vmStoreOwner, factory)

    @Suppress("UNCHECKED_CAST")
    provider[key, RetainedElmStore::class.java].store
      as Store<Event, Effect, State>
  }
}
