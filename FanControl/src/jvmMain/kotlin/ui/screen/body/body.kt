package ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.MainViewModel
import ui.UiStates
import ui.component.behavior.fan
import ui.component.control.control
import ui.component.sensor.temp
import ui.component.utils.managerTextHomeScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun body(viewModel: MainViewModel, uiStates: State<UiStates>) {
    Row(
        modifier = Modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        LazyColumn {
            stickyHeader {
                managerTextHomeScreen(
                    text = "Fan"
                )
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )
            }
            itemsIndexed(uiStates.value.fanList) { index, item ->
                Spacer(
                    modifier = Modifier
                        .height(5.dp)
                )
                fan(
                    fan = item,
                    viewModel = viewModel,
                    index = index
                )
            }
        }

        LazyColumn {
            stickyHeader {
                managerTextHomeScreen(
                    text = "Temp"
                )
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )
            }

            itemsIndexed(uiStates.value.tempList) { index, item ->
                Spacer(
                    modifier = Modifier
                        .height(5.dp)
                )
                temp(
                    temp = item,
                    viewModel = viewModel,
                    index = index
                )
            }
        }

        LazyColumn {
            stickyHeader {
                managerTextHomeScreen(
                    text = "Control"
                )
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )
            }

            itemsIndexed(uiStates.value.controlList) { index, item ->
                Spacer(
                    modifier = Modifier
                        .height(5.dp)
                )
                control(
                    control = item,
                    viewModel = viewModel,
                    index = index
                )
            }
        }
    }
}