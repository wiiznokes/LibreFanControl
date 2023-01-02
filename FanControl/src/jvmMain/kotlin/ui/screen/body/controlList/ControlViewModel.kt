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
    private fun updateSafely(index: Int, isAuto: Boolean, behaviorId: Long?): Boolean {
        if (controlsChange.value)
            return false

        controlItemList[index].isAuto = isAuto
        controlItemList[index].behaviorId = behaviorId

        controlsChange.value = true
        println("controlsChange = true, in control View Model")

        return true
    }

    fun remove(index: Int) {
        if (updateSafely(
                index = index,
                isAuto = true,
                behaviorId = controlItemList[index].behaviorId
            )
        ) {
            controlItemList[index].visible = false

        }
    }

    fun setBehavior(index: Int, behaviorId: Long?) {
        updateSafely(
            index = index,
            isAuto = controlItemList[index].isAuto,
            behaviorId = behaviorId
        )
    }

    fun onSwitchClick(checked: Boolean, index: Int) {
        updateSafely(
            index = index,
            isAuto = !checked,
            behaviorId = controlItemList[index].behaviorId
        )
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