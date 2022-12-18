package ui.screen.addItem.control

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Control
import model.item.ControlItem
import ui.component.baseControlAddItem
import ui.utils.Resources


val viewModel = AddControlViewModel()


@Composable
fun managerAddControl() {
    LazyColumn {

        val previousIndexList = mutableListOf<Int>()

        itemsIndexed(viewModel.controlItemList.value.filterIndexed { index, controlItem ->
            if (!controlItem.visible)
                previousIndexList.add(index)
            !controlItem.visible
        }) { index, it ->

            controlAddItem(
                controlItem = it,
                index = previousIndexList[index],
                controlList = viewModel.controlList.value,
                addControl = {
                    viewModel.addControl(it)
                }
            )

        }
    }
}


@Composable
private fun controlAddItem(
    controlItem: ControlItem,
    index: Int,
    controlList: SnapshotStateList<Control>,
    addControl: (Int) -> Unit
) {

    val control = controlList.find {
        it.libId == controlItem.sensorId
    }


    baseControlAddItem(
        name = controlItem.name,
        onEditClick = {
            addControl(index)
        },
        behaviorName = controlItem.behaviorName,
        value = "${control!!.value} ${Resources.getString("unity/percent")}",
        fanValue = ""
    )
}