package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.component.behavior.fan
import ui.component.control.control
import ui.component.sensor.temp
import ui.component.utils.managerTextHomeScreen
import ui.screen.body


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun home(viewModel: MainViewModel) {
    val uiStates = viewModel.uiState.collectAsState()


    body(
        viewModel = viewModel,
        uiStates = uiStates
    )
}





