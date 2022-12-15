package ui.screen.body.controlList


import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import model.item.ControlItem
import ui.component.baseControlBody
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


    baseControlBody(
        onNameChange = { viewModel.setName(it, index) },
        editModeActivated = editModeActivated,
        onEditClick = {
            println("index = $index")
            viewModel.remove(index)
        },

        onSwitchClick = {
            viewModel.setControl(
                index = index,
                libIndex = control.libIndex,
                isAuto = it,
                value = control.value
            )
        },
        value = "${control.value} ${Resources.getString("unity/percent")}",
        fanValue = "",
        behaviorItemList = viewModel.behaviorItemList.value,
        onItemClick = {
            viewModel.setBehavior(index, it)
        },
        control = controlItem
    )
}



