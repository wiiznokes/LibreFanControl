package ui.screen.addItem.controlList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.control.Control

class AddControlViewModel(
    private val _controlList: MutableStateFlow<SnapshotStateList<Control>> = State._controlList,
    private val _addControlList: MutableStateFlow<SnapshotStateList<Control>> = State._addControlList
) {
    fun add(index: Int) {
        _addControlList.update {
            val tempBehavior = it.removeAt(index)
            _controlList.update { it2 ->
                _controlList.value.add(tempBehavior)
                it2
            }
            it
        }
    }
}