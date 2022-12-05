package ui.screen.body

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import ui.component.itemsList
import ui.screen.body.behaviorList.behavior
import ui.screen.body.behaviorList.behaviorList
import ui.screen.body.controlList.control
import ui.screen.body.controlList.controlList
import ui.screen.body.fanList.fan
import ui.screen.body.fanList.fanList
import ui.screen.body.tempList.temp
import ui.screen.body.tempList.tempList
import ui.utils.Resources

@Composable
fun body(
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
            itemsList(
                title = Resources.getString("title_fan")
            ){
                fanList(editModeActivated.value)
            }
            itemsList(
                title = Resources.getString("title_temp")
            ){
                tempList(editModeActivated.value)
            }
            itemsList(
                title = Resources.getString("title_control")
            ){
                controlList(editModeActivated.value)
            }
            itemsList(
                title = Resources.getString("title_behavior")
            ){
                behaviorList(editModeActivated.value)
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