package ui.screen.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import ui.screen.drawer.firstView.baseSetting
import ui.screen.drawer.secondView.baseSecondView

private val viewModel = DrawerVM()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun drawer(
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    baseSecondView(
        title = "theme"
    ) {

    }
    /*
    baseSetting(
        drawerState = drawerState,
        scope = scope
    )

     */
}