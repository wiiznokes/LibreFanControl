package ui.screen.body.controlList


import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import model.item.ControlItem
import ui.component.baseControlBody
import ui.utils.Resources


private val viewModel: ControlViewModel = ControlViewModel()


fun LazyListScope.controlList() {


    val previousIndexList = mutableListOf<Int>()

    itemsIndexed(viewModel.controlItemList.filterIndexed { index, controlItem ->
        if (controlItem.visible)
            previousIndexList.add(index)
        controlItem.visible
    }) { index, it ->

        control(
            control = it,
            index = previousIndexList[index]
        )
    }
}


@Composable
fun control(
    control: ControlItem,
    index: Int
) {
    baseControlBody(
        onNameChange = { viewModel.setName(it, index) },
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
        behaviorItemList = viewModel.behaviorItemList,
        onBehaviorChange = {
            viewModel.setBehavior(index, it)
        },
        control = control,
        enabled = !viewModel.controlsChange.collectAsState().value
    )
}



