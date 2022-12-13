package ui.screen.addItem

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import model.item.ControlItem
import ui.component.baseControlAddItem
import ui.screen.body.controlList.ControlViewModel


private val viewModel: ControlViewModel = ControlViewModel()

@Composable
fun controlList(
) {
    LazyColumn {

        val previousIndexList = mutableListOf<Int>()

        itemsIndexed(viewModel.controlItemList.value.filterIndexed { index, controlItem ->
            if (!controlItem.visible)
                previousIndexList.add(index)
            !controlItem.visible
        }) { index, it ->

            controlAddItem(
                controlItem = it,
                index = index
            )

        }
    }
}


@Composable
fun controlAddItem(
    controlItem: ControlItem,
    index: Int,
) {

    val control = viewModel.controlList.value.filter {
        it.libId == controlItem.sensorId
    }[0]


    baseControlAddItem(
        name = controlItem.name,
        onEditClick = {},
        behaviorName = controlItem.behaviorName,
        value = "${control.value} %",
        fanValue = ""
    )
}