package ui.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import ui.screen.addItem.addItem
import ui.screen.body.body
import ui.screen.drawer.drawer
import ui.screen.topBar.topBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun home() {

    val viewModel = HomeViewModel()


    ModalNavigationDrawer(
        modifier = Modifier,
        drawerContent = {
            drawer(

            )
        },
        drawerState = viewModel.drawerState.value,
        gesturesEnabled = true
    ) {
        val addItemExpanded = viewModel.addItemExpanded.value

        Column {
            topBar()

            // body + addItem
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        if (addItemExpanded.value) {
                            addItem()
                        }

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












