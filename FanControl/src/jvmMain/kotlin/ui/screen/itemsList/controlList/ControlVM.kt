package ui.screen.itemsList.controlList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import logicControl.isControlChange
import model.item.ControlItem
import model.item.behavior.BehaviorItem
import utils.checkNameTaken

class ControlVM(
    val controlItemList: SnapshotStateList<ControlItem> = State.controlItemList,
    val behaviorItemList: SnapshotStateList<BehaviorItem> = State.behaviorItemList,
    val controlsChange: MutableStateFlow<Boolean> = State.controlsChange
) {

    fun addControl(index: Int) {
        controlItemList[index] = controlItemList[index].copy(
            visible = true
        )
    }

    fun remove(index: Int) {
        updateSafely(index) {
            controlItemList[index] = controlItemList[index].copy(
                isAuto = true,
                visible = false
            )
        }
    }

    fun setBehavior(index: Int, behaviorId: Long?) {
        updateSafely(index) {
            controlItemList[index] = controlItemList[index].copy(
                behaviorId = behaviorId
            )
        }
    }

    fun onSwitchClick(checked: Boolean, index: Int) {
        updateSafely(index) {
            controlItemList[index] = controlItemList[index].copy(
                isAuto = !checked
            )
        }
    }


    fun setName(name: String, index: Int) {
        checkNameTaken(
            names = controlItemList.map { item ->
                item.name
            },
            name = name,
            index = index
        )
        controlItemList[index] = controlItemList[index].copy(
            name = name
        )
    }

    private fun updateSafely(index: Int, operation: () -> Unit) {
        if (controlsChange.value)
            return

        val previousControl = controlItemList[index].copy()
        operation()
        if (isControlChange(previousControl, controlItemList[index]))
            controlsChange.value = true
    }
}