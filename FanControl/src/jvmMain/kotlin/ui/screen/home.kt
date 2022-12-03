package ui

import androidx.compose.runtime.Composable
import ui.screen.HomeViewModel
import ui.screen.body.component.body
import ui.utils.ViewModelFactory


@Composable
fun home() {

    val viewModel: HomeViewModel

    if (ViewModelFactory.homeViewModel == null) {
        ViewModelFactory.homeViewModel = HomeViewModel().also {
            viewModel = it
        }
    } else {
        viewModel = ViewModelFactory.homeViewModel!!
    }

    body(
        fans = viewModel.fanList,
        temps = viewModel.tempList,
        controls = viewModel.controlList,
        behaviors = viewModel.behaviorList
    )
}





