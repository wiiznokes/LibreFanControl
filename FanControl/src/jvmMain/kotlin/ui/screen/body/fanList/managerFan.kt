package ui.screen.body.fanList

import Source
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.StateFlow
import model.hardware.sensor.Fan
import ui.component.baseSensor
import ui.utils.Resources

@Composable
fun fan(
    fan: Fan,
    index: Int,
    editModeActivated: StateFlow<Boolean>
) {
    val viewModel = FanViewModel()

    baseSensor(
        iconPainter = Resources.getIcon("toys_fan"),
        iconContentDescription = "",
        name = fan.name,
        label = "name",
        onNameChange = {
            viewModel.setName(it, index)
        },
        editModeActivated = editModeActivated,
        onEditClick = {
            viewModel.remove(index)
        },
        source = Source.BODY,
        sensorName = fan.libName,
        onChangeSensorClick = {

        }
    )
}

