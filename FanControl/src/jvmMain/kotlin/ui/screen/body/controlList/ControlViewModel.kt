package ui.screen.body.controlList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.Control
import model.item.BehaviorItem
import model.item.ControlItem

class ControlViewModel(
    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
    private val _controlList: MutableStateFlow<SnapshotStateList<Control>> = State._controlList,
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList,
) {
    val controlList = _controlList.asStateFlow()
    val controlItemList = _controlItemList.asStateFlow()


    fun remove(index: Int) {
        _controlItemList.update {
            _controlItemList.value.removeAt(index)
            it
        }
    }

    fun setControl(libIndex: Int, isAuto: Boolean, value: Int) {

    }


    fun setName(name: String, index: Int) {
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