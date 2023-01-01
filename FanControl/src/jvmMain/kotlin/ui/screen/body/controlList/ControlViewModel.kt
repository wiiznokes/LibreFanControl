package ui.screen.body.controlList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.item.ControlItem
import model.item.behavior.BehaviorItem
import utils.checkNameTaken

class ControlViewModel(
    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
    val behaviorItemList: StateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList.asStateFlow(),
    private val _controlsChange: MutableStateFlow<Boolean> = State._controlsChange
) {
    val controlItemList = _controlItemList.asStateFlow()
    val controlChange = _controlsChange.asStateFlow()

    private fun updateSafely(index: Int, isAuto: Boolean, behaviorId: Long?, visible: Boolean? = null) {
        if (controlChange.value)
            return

        _controlItemList.update {
            println("controlsChange = true, in control View Model")
            _controlsChange.value = true
            it[index].isAuto = isAuto
            it[index].behaviorId = behaviorId
            if (visible != null)
                it[index].visible = visible
            it
        }
    }

    fun remove(index: Int) {
        updateSafely(
            index = index,
            isAuto = true,
            behaviorId = controlItemList.value[index].behaviorId,
            visible = false
        )
    }

    fun setBehavior(index: Int, behaviorId: Long?) {
        updateSafely(
            index = index,
            isAuto = controlItemList.value[index].isAuto,
            behaviorId = behaviorId
        )
    }

    fun onSwitchClick(checked: Boolean, index: Int) {
        updateSafely(
            index = index,
            isAuto = !checked,
            behaviorId = controlItemList.value[index].behaviorId
        )
    }


    fun setName(name: String, index: Int) {
        checkNameTaken(
            names = _controlItemList.value.map { item ->
                item.name
            },
            name = name,
            index = index
        )
        _controlItemList.update {
            it[index] = it[index].copy(
                name = name
            )
            it
        }
    }
}