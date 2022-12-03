package ui.screen.body.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow
import model.behavior.Behavior
import model.hardware.control.Control
import model.hardware.sensor.Fan
import model.hardware.sensor.Temp
import ui.component.control.control
import ui.component.sensor.temp
import ui.screen.body.behaviorList.component.behavior
import ui.screen.body.fanList.component.fan
import ui.screen.component.managerTextHomeScreen
import ui.utils.Resources.Companion.getString


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun managerBodyListItem(
    title: String,
    fans: StateFlow<SnapshotStateList<Fan>>,
    temps: StateFlow<SnapshotStateList<Temp>>,
    controls: StateFlow<SnapshotStateList<Control>>,
    behaviors: StateFlow<SnapshotStateList<Behavior>>,
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
                itemsIndexed(fans.value) { index, item ->
                    Spacer(
                        modifier = Modifier
                            .height(5.dp)
                    )
                    fan(
                        fan = item,
                        index = index
                    )
                }
            }

            getString("title_temp") -> {
                itemsIndexed(temps.value) { index, item ->
                    Spacer(
                        modifier = Modifier
                            .height(5.dp)
                    )
                    temp(
                        temp = item,
                        index = index
                    )
                }
            }

            getString("title_control") -> {
                itemsIndexed(controls.value) { index, item ->
                    Spacer(
                        modifier = Modifier
                            .height(5.dp)
                    )
                    control(
                        control = item,
                        index = index
                    )
                }
            }

            getString("title_behavior") -> {
                itemsIndexed(behaviors.value) { index, item ->
                    Spacer(
                        modifier = Modifier
                            .height(5.dp)
                    )
                    behavior(
                        behavior = item,
                        index = index
                    )
                }
            }
        }

    }
}
