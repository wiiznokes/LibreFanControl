package ui.screen.addItem.control

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.item.ControlItem

class AddControlViewModel(
    val controlItemList: SnapshotStateList<ControlItem> = State.controlItemList,
) {

    fun addControl(index: Int) {
        controlItemList[index] = controlItemList[index].copy(
            visible = true
        )
    }
}