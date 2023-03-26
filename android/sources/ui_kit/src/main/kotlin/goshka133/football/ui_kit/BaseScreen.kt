package goshka133.football.ui_kit

import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.generateScreenKey

abstract class BaseScreen : Screen(generateScreenKey()) {

    override val defaultViewModelProviderFactory: ViewModelProvider.Factory
        get() = TODO("Not yet implemented")
}
