package ui.screen.addItem

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Control
import model.item.ControlItem
import ui.component.baseControlAddItem


@Composable
fun baseControlListAddItem(
    controlItemList: SnapshotStateList<ControlItem>,
    controlList: SnapshotStateList<Control>,
    addControl: (Int) -> Unit
) {
    LazyColumn {

        val previousIndexList = mutableListOf<Int>()

        itemsIndexed(controlItemList.filterIndexed { index, controlItem ->
            if (!controlItem.visible)
                previousIndexList.add(index)
            !controlItem.visible
        }) { index, it ->

            controlAddItem(
                controlItem = it,
                index = previousIndexList[index],
                controlList = controlList,
                addControl = addControl
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

    val control = controlList.filter {
        it.libId == controlItem.sensorId
    }[0]


    baseControlAddItem(
        name = controlItem.name,
        onEditClick = {
            addControl(index)
        },
        behaviorName = controlItem.behaviorName,
        value = "${control.value} %",
        fanValue = ""
    )
}