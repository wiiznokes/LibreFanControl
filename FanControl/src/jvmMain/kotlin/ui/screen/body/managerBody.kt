package ui.screen.body

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.StateFlow
import model.behavior.Behavior
import model.hardware.control.Control
import model.hardware.sensor.Fan
import model.hardware.sensor.Temp
import ui.screen.body.behaviorList.behavior
import ui.screen.body.controlList.control
import ui.screen.body.fanList.fan
import ui.screen.body.tempList.temp
import ui.utils.Resources

@Composable
fun body(
    fans: StateFlow<SnapshotStateList<Fan>>,
    temps: StateFlow<SnapshotStateList<Temp>>,
    controls: StateFlow<SnapshotStateList<Control>>,
    behaviors: StateFlow<SnapshotStateList<Behavior>>,
    editModeActivated: StateFlow<Boolean>
) {

    val viewModel = BodyViewModel()

    Box {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.background
                ),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            listOf(
                Resources.getString("title_fan"),
                Resources.getString("title_temp"),
                Resources.getString("title_control"),
                Resources.getString("title_behavior")
            ).forEach {
                managerBodyListItem(
                    title = it,
                    fans = fans,
                    temps = temps,
                    controls = controls,
                    behaviors = behaviors,
                    editModeActivated = editModeActivated

                )
            }
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            onClick = {

            }
        ) {
            Text(
                text = "Add"
            )
        }
    }

}

@Composable
fun managerBodyListItem(
    title: String,
    fans: StateFlow<SnapshotStateList<Fan>>,
    temps: StateFlow<SnapshotStateList<Temp>>,
    controls: StateFlow<SnapshotStateList<Control>>,
    behaviors: StateFlow<SnapshotStateList<Behavior>>,
    editModeActivated: StateFlow<Boolean>
) {

    LazyColumn {
        item {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 40.sp
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
        }
        when (title) {
            Resources.getString("title_fan") -> {
                itemsIndexed(fans.value) { index, item ->
                    fan(
                        fan = item,
                        index = index,
                        editModeActivated
                    )
                }
            }

            Resources.getString("title_temp") -> {
                itemsIndexed(temps.value) { index, item ->
                    temp(
                        temp = item,
                        index = index,
                        editModeActivated = editModeActivated
                    )
                }
            }

            Resources.getString("title_control") -> {
                itemsIndexed(controls.value) { index, item ->
                    control(
                        control = item,
                        index = index,
                        editModeActivated = editModeActivated
                    )
                }
            }

            Resources.getString("title_behavior") -> {
                itemsIndexed(behaviors.value) { index, item ->
                    behavior(
                        behavior = item,
                        index = index,
                        editModeActivated = editModeActivated
                    )
                }
            }
        }

    }
}