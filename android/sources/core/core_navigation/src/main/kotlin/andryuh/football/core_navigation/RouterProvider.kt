package andryuh.football.core_navigation

import androidx.compose.runtime.compositionLocalOf
import com.github.terrakok.modo.NavigationContainer
import com.github.terrakok.modo.stack.StackState
import javax.inject.Inject
import javax.inject.Singleton

val LocalRouter =
    compositionLocalOf<Router> { error("StackNavModel wasn't provided") }

typealias Router = NavigationContainer<StackState>

@Singleton
class RouterProvider @Inject constructor() {

    var router: Router? = null
        private set

    fun setRouter(router: Router) {
        this.router = router
    }
}