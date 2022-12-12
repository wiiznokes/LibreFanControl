package ui.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.screen.addItem.addItem
import ui.screen.body.body
import ui.screen.drawer.drawer
import ui.screen.topBar.addItemTopBar
import ui.screen.topBar.mainTopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun home() {

    val viewModel = HomeViewModel()

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = Modifier,
        drawerContent = {
            drawer(

            )
        },
        drawerState = drawerState,
        gesturesEnabled = true
    ) {
        val addItemExpanded = viewModel.addItemExpanded.value


        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                    if (addItemExpanded.value) {
                        Column {
                            val modifier = Modifier
                                .widthIn(min = 200.dp, max = Dp.Infinity)
                                .fillMaxWidth(0.2f)

                            addItemTopBar(
                                modifier = modifier
                            )
                            addItem(
                                modifier = modifier
                            )
                        }
                    }

                    Column {
                        mainTopBar(
                            onNavigationIconClick = {
                                scope.launch { drawerState.open() }
                            }
                        )
                        body(
                            editModeActivated = viewModel.editModeActivated.value,
                            addItemExpanded = addItemExpanded
                        )
                    }
                }
            }
        }

    }
}












