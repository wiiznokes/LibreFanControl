package ui.screen.body.fanList

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.StateFlow
import model.hardware.sensor.Fan
import ui.screen.component.managerBaseSensorBody

@Composable
fun fan(
    fan: Fan,
    index: Int,
    editModeActivated: StateFlow<Boolean>
) {
    val viewModel = FanViewModel()

    managerBaseSensorBody(
        editModeActivated = editModeActivated,
        onRemove = {
            viewModel.remove(index)
        },
        name = fan.name,
        onNameChange = {
            viewModel.setName(
                name = it,
                index = index
            )
        },
        value = fan.value.toString(),
        suffix = "RMP"
    )
}

