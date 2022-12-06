package ui.screen.body.controlList

import Source
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import model.hardware.Control
import ui.component.baseControl
import ui.utils.Resources


private val viewModel: ControlViewModel = ControlViewModel()
fun LazyListScope.controlList(
    editModeActivated: Boolean
) {

    itemsIndexed(viewModel.controlList.value.filter {
        it.isVisible
    }) { index, control ->
        control(
            control = control,
            index = index,
            editModeActivated = editModeActivated
        )
    }
}


@Composable
fun control(
    control: Control,
    index: Int,
    editModeActivated: Boolean
) {

    baseControl(
        iconPainter = Resources.getIcon("alternate_email"),
        iconContentDescription = "",
        name = viewModel.controlItemList.value.filter {
            it.sensorId == control.libId
        }[0].name,
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



