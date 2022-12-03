package ui

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import ui.screen.HomeViewModel
import ui.screen.body.body
import ui.screen.drawer.component.drawer
import ui.screen.topBar.component.topBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun home() {

    val viewModel = HomeViewModel()

    val drawerState = rememberDrawerState(DrawerValue.Closed)


    ModalNavigationDrawer(
        drawerContent = {
            drawer(

            )
        },
        drawerState = drawerState,
        gesturesEnabled = true
    ) {

        topBar()

        body(
            fans = viewModel.fanList,
            temps = viewModel.tempList,
            controls = viewModel.controlList,
            behaviors = viewModel.behaviorList
        )
    }
}





