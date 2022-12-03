package ui.screen.body

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.MainViewModel
import ui.UiStates
import ui.component.behavior.behavior
import ui.component.control.control
import ui.component.sensor.fan
import ui.component.sensor.temp
import ui.component.utils.managerTextHomeScreen
import ui.utils.Resources.Companion.getString


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun managerBodyListItem(
    title: String,
    viewModel: MainViewModel,
    uiStates: State<UiStates>
) {
    LazyColumn {
        stickyHeader {
            managerTextHomeScreen(
                text = title
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
        }
        when (title) {
            getString("title_fan") -> {
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

            getString("title_temp") -> {
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

            getString("title_control") -> {
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

            getString("title_behavior") -> {
                itemsIndexed(uiStates.value.behaviorList) { index, item ->
                    Spacer(
                        modifier = Modifier
                            .height(5.dp)
                    )
                    behavior(
                        behavior = item,
                        viewModel = viewModel,
                        index = index
                    )
                }
            }
        }

    }
}
