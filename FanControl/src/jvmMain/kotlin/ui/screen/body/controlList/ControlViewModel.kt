package ui.screen.body.controlList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import external.ExternalManager
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


    fun remove(index: Int, libIndex: Int) {
        Application.setControl(
            libIndex = libIndex,
            isAuto = false
        )

        _controlItemList.update {
            _controlItemList.value[index] = _controlItemList.value[index].copy(
                visible = false,
                isActive = false
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

    fun setControl(libIndex: Int, isAuto: Boolean) {
        // we set control only if is Auto is false because
        // another class is in charge to know if control should be set
        if(!isAuto) {
            Application.setControl(
                libIndex = libIndex,
                isAuto = false
            )
        }

        _controlItemList.update {
            _controlItemList.value[libIndex] = _controlItemList.value[libIndex].copy(
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
}