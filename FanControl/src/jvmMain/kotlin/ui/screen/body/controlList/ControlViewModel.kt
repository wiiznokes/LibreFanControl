package ui.screen.body.controlList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.control.Control

class ControlViewModel(
    private val _controlList: MutableStateFlow<SnapshotStateList<Control>> = State._controlList,
    private val _addControlList: MutableStateFlow<SnapshotStateList<Control>> = State._controlList
) {

    val controlList = State._controlList.asStateFlow()

    fun remove(index: Int) {
        _controlList.update {
            val tempBehavior = it.removeAt(index)
            _addControlList.update { it2 ->
                _addControlList.value.add(tempBehavior)
                it2
            }
            it
        }
    }

    fun setControl(libIndex: Int, isAuto: Boolean, value: Int) {

    }


    fun setName(name: String, index: Int) {

        _controlList.update {
            _controlList.value[index] = _controlList.value[index].copy(
                name = name
            )
            it
        }
    }

    fun setBehaviorId(index: Int, id: String) {
        _controlList.update {
            _controlList.value[index] = _controlList.value[index].copy(
                behaviorId = id
            )
            it
        }
    }
}