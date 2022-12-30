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
    val behaviorItemList: StateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList.asStateFlow()
) {
    val controlItemList = _controlItemList.asStateFlow()

    private fun changeLogic(index: Int, isAuto: Boolean, behaviorId: Long?): Boolean {
        if (!controlItemList.value[index].logicHasVerify)
            return false

        _controlItemList.update {
            it[index].logicHasVerify = false
            it[index].isAuto = isAuto
            it[index].behaviorId = behaviorId
            it
        }
        return true
    }

    fun remove(index: Int) {
        if (!changeLogic(
            index = index,
            isAuto = true,
            behaviorId = controlItemList.value[index].behaviorId
        )) return

        _controlItemList.update {
            it[index].visible = false
            it
        }
    }

    fun setBehavior(index: Int, behaviorId: Long?) {
        changeLogic(
            index = index,
            isAuto = false,
            behaviorId = behaviorId
        )

    }

    fun onSwitchClick(checked: Boolean, index: Int) {
        changeLogic(
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