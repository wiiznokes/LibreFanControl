package ui.screen.addItem.control

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import model.item.ControlItem
import ui.component.baseControlAddItem
import ui.utils.Resources


val viewModel = AddControlViewModel()


@Composable
fun managerAddControl() {
    LazyColumn {

        val previousIndexList = mutableListOf<Int>()

        itemsIndexed(viewModel.controlItemList.filterIndexed { index, controlItem ->
            if (!controlItem.visible)
                previousIndexList.add(index)
            !controlItem.visible
        }) { index, it ->

            controlAddItem(
                control = it,
                index = previousIndexList[index]
            )

        }
    }
}


@Composable
private fun controlAddItem(
    control: ControlItem,
    index: Int
) {


    baseControlAddItem(
        name = control.name,
        onEditClick = {
            viewModel.addControl(index)
        },
        behaviorName = Resources.getString("add_item/choose_behavior"),
        value = "${control.value} ${Resources.getString("unity/percent")}",
        fanValue = ""
    )
}