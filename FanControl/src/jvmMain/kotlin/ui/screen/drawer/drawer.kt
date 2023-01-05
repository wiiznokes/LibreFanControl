package ui.screen.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import ui.screen.drawer.firstView.baseSetting
import ui.screen.drawer.firstView.baseSettingItem

private val viewModel = DrawerVM()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun drawer(
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    baseSetting(
        drawerState = drawerState,
        scope = scope
    ) {
        items(mainSetting) {
            baseSettingItem(
                item = it,
                onClick = { println("on click") }
            )
        }

        item {

        }


        items(mainSetting) {
            baseSettingItem(
                item = it,
                onClick = { println("on click") }
            )
        }
    }
}





