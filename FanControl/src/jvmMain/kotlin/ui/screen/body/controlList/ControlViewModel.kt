package ui.screen.body.controlList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.Control
import model.item.Behavior
import model.item.LibItem

class ControlViewModel(
    private val _controlItemList: MutableStateFlow<SnapshotStateList<LibItem>> = State._controlItemList,
    private val _controlList: MutableStateFlow<SnapshotStateList<Control>> = State._controlList,
    private val _behaviorList: MutableStateFlow<SnapshotStateList<Behavior>> = State._behaviorItemList,
) {
    val controlList = State._controlList.asStateFlow()
    val controlItemList = State._controlItemList.asStateFlow()


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
                sensorName = _behaviorList.value[menuIndex].name
            )
            it
        }
    }
}