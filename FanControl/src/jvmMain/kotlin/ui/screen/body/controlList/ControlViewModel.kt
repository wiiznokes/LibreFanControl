package ui.screen.body.controlList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.item.ControlItem
import model.item.behavior.BehaviorItem
import utils.checkNameTaken

class ControlViewModel(
    val controlItemList: SnapshotStateList<ControlItem> = State.controlItemList,
    val behaviorItemList: SnapshotStateList<BehaviorItem> = State.behaviorItemList,
    val controlsChange: MutableStateFlow<Boolean> = State.controlsChange
) {
    private fun updateSafely(operation: () -> Unit): Boolean {
        if (controlsChange.value)
            return false

        operation()

        controlsChange.value = true
        return true
    }

    fun remove(index: Int) {
        updateSafely {
            controlItemList[index] = controlItemList[index].copy(
                isAuto = true,
                visible = false
            )
        }
    }

    fun setBehavior(index: Int, behaviorId: Long?) {
        updateSafely {
            controlItemList[index] = controlItemList[index].copy(
                behaviorId = behaviorId
            )
        }
    }

    fun onSwitchClick(checked: Boolean, index: Int) {
        updateSafely {
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
}