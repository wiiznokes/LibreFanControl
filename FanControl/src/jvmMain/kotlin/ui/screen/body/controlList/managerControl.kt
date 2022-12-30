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
            control = it,
            index = previousIndexList[index],
            editModeActivated = editModeActivated
        )
    }
}


@Composable
fun control(
    control: ControlItem,
    index: Int,
    editModeActivated: Boolean
) {
    baseControlBody(
        onNameChange = { viewModel.setName(it, index) },
        editModeActivated = editModeActivated,
        onEditClick = {
            viewModel.remove(
                index = index
            )
        },

        onSwitchClick = { checked ->
            viewModel.onSwitchClick(
                checked = checked,
                index = index
            )
        },
        value = "${control.value} ${Resources.getString("unity/percent")}",
        fanValue = "",
        behaviorItemList = viewModel.behaviorItemList.value,
        onBehaviorChange = {
            viewModel.setBehavior(index, it)
        },
        control = control
    )
}



