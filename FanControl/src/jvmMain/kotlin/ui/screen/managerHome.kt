package ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

        Column {
            topBar()

            body(
                fans = viewModel.fanList,
                temps = viewModel.tempList,
                controls = viewModel.controlList,
                behaviors = viewModel.behaviorList,
                editModeActivated = viewModel.editModeActivated
            )
        }
    }
}





