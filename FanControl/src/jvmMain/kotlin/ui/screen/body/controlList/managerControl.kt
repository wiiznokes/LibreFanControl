package ui.screen.body.controlList

import Source
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.StateFlow
import model.hardware.control.Control
import ui.component.baseControl
import ui.utils.Resources

@Composable
fun control(control: Control,
            index: Int,
            editModeActivated: StateFlow<Boolean>
) {
    val viewModel = ControlViewModel()

    baseControl(
        iconPainter = Resources.getIcon("add"),
        iconContentDescription = "",
        name = control.name,
        label = "name",
        onNameChange = { viewModel.setName(it, index) },
        editModeActivated = editModeActivated,
        onEditClick = { viewModel.remove(index) },
        source = Source.BODY,

        behaviorName = "",
        onChangeBehaviorClick = {},
        isActive = !control.isAuto,
        onSwitchClick = {
            viewModel.setControl(
                libIndex = control.libIndex,
                isAuto = it,
                value = control.value
            )
        },
        value = "${control.value} %",
        fanValue = ""
    )
}



