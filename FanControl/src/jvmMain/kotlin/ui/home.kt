package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import ui.screen.body.body


@Composable
fun home(viewModel: MainViewModel) {
    val uiStates = viewModel.uiState.collectAsState()


    body(
        viewModel = viewModel,
        uiStates = uiStates
    )
}





