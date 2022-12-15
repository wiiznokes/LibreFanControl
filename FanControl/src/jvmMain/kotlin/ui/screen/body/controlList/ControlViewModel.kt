package ui.screen.body.controlList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.Control
import model.item.ControlItem
import model.item.behavior.BehaviorItem
import ui.utils.Resources
import ui.utils.checkNameTaken

class ControlViewModel(
    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
    private val _controlList: MutableStateFlow<SnapshotStateList<Control>> = State._controlList,
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList,
) {
    val controlList = _controlList.asStateFlow()
    val controlItemList = _controlItemList.asStateFlow()
    val behaviorItemList = _behaviorItemList.asStateFlow()


    fun remove(index: Int) {
        _controlItemList.update {
            _controlItemList.value[index] = _controlItemList.value[index].copy(
                visible = false
            )
            it
        }
    }

    fun setBehavior(index: Int, behaviorItem: BehaviorItem?) {
        if (behaviorItem != null) {
            _controlItemList.update {
                _controlItemList.value[index] = _controlItemList.value[index].copy(
                    behaviorName = behaviorItem.name
                )
                it
            }
        } else {
            _controlItemList.update {
                _controlItemList.value[index] = _controlItemList.value[index].copy(
                    behaviorName = Resources.getString("none")
                )
                it
            }
        }

    }

    fun setControl(index: Int, libIndex: Int, isAuto: Boolean, value: Int) {
        _controlItemList.update {
            _controlItemList.value[index] = _controlItemList.value[index].copy(
                isActive = isAuto
            )
            it
        }
    }


    fun setName(name: String, index: Int) {

        checkNameTaken(_controlItemList.value, name, index)

        _controlItemList.update {
            _controlItemList.value[index] = _controlItemList.value[index].copy(
                name = name
            )
            it
        }
    }

    fun changeBehaviorId(index: Int, menuIndex: Int) {
        _controlItemList.update {
            _controlItemList.value[index] = _controlItemList.value[index].copy(
                sensorName = _behaviorItemList.value[menuIndex].name
            )
            it
        }
    }
}