package ui.screen.body.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.StateFlow
import model.behavior.Behavior
import model.hardware.control.Control
import model.hardware.sensor.Fan
import model.hardware.sensor.Temp
import ui.screen.body.BodyViewModel
import ui.utils.Resources
import ui.utils.ViewModelFactory

@Composable
fun body(
    fans: StateFlow<SnapshotStateList<Fan>>,
    temps: StateFlow<SnapshotStateList<Temp>>,
    controls: StateFlow<SnapshotStateList<Control>>,
    behaviors: StateFlow<SnapshotStateList<Behavior>>,
) {

    val viewModel: BodyViewModel

    if (ViewModelFactory.bodyViewModel == null) {
        ViewModelFactory.bodyViewModel = BodyViewModel().also {
            viewModel = it
        }
    } else {
        viewModel = ViewModelFactory.bodyViewModel!!
    }


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
                behaviors = behaviors
            )
        }
    }
}