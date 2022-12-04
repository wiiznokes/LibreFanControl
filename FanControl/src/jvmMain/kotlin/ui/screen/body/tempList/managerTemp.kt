package ui.screen.body.tempList


import Source
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.StateFlow
import model.hardware.sensor.Temp
import ui.component.baseSensor
import ui.utils.Resources

@Composable
fun temp(
    temp: Temp,
    index: Int,
    editModeActivated: StateFlow<Boolean>
) {
    val viewModel = TempViewModel()

    baseSensor(
        iconPainter = Resources.getIcon("thermometer"),
        iconContentDescription = "",
        name = temp.name,
        label = "name",
        onNameChange = {
            viewModel.setName(it, index)
        },
        editModeActivated = editModeActivated,
        onEditClick = {
            viewModel.remove(index)
        },
        source = Source.BODY,
        sensorName = temp.libName,
        onChangeSensorClick = {

        },
        sensorValue = "${temp.value} °C"
    )
}


