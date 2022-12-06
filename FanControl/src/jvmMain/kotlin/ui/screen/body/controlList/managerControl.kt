package ui.screen.body.controlList

import Source
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import model.item.ControlItem
import ui.component.baseControl
import ui.utils.Resources


private val viewModel: ControlViewModel = ControlViewModel()


fun LazyListScope.controlList(
    editModeActivated: Boolean
) {


    val previousIndexList = mutableListOf<Int>()
    itemsIndexed(viewModel.controlItemList.value.filterIndexed { index, controlItem ->
        if (controlItem.visible)
            previousIndexList.add(index)
        controlItem.visible
    }) { index, it ->
        control(
            controlItem = it,
            index = previousIndexList[index],
            editModeActivated = editModeActivated
        )
    }


    itemsIndexed(viewModel.controlItemList.value.filter {
        it.visible
    }) { index, control ->
        control(
            controlItem = control,
            index = index,
            editModeActivated = editModeActivated
        )
    }
}


@Composable
fun control(
    controlItem: ControlItem,
    index: Int,
    editModeActivated: Boolean
) {

    val control = viewModel.controlList.value.filter {
        it.libId == controlItem.sensorId
    }[0]


    baseControl(
        iconPainter = Resources.getIcon("alternate_email"),
        iconContentDescription = "",
        name = controlItem.name,
        label = "name",
        onNameChange = { viewModel.setName(it, index) },
        editModeActivated = editModeActivated,
        onEditClick = { viewModel.remove(index) },
        source = Source.BODY,

        behaviorName = "",
        isActive = !control.isAuto,
        onSwitchClick = {
            viewModel.setControl(
                libIndex = control.libIndex,
                isAuto = it,
                value = control.value
            )
        },
        value = "${control.value} %",
        fanValue = "",
        behaviorItemList = viewModel.behaviorItemList.value,
        onItemClick = {
            viewModel.setBehavior(index, it)
        }
    )
}



