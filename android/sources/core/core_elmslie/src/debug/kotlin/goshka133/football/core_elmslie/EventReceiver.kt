package goshka133.football.core_elmslie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import vivid.money.elmslie.core.store.Store

typealias EventReceiver<Event> = (event: Event) -> Unit

@Composable
fun <Event, Effect, State> Store<Event, Effect, State>.rememberEventReceiver():
  EventReceiver<Event> {

  return remember(this) { { event -> accept(event) } }
}
