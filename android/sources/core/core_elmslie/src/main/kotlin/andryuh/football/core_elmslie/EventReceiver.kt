package andryuh.football.core_elmslie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import vivid.money.elmslie.core.store.Store

typealias EventReceiver<Event> = (event: Event) -> Unit

@Composable
@Stable
fun <Event, Effect, State> Store<Event, Effect, State>.rememberEventReceiver():
  EventReceiver<Event> {

  return remember(this) { { event -> accept(event) } }
}
