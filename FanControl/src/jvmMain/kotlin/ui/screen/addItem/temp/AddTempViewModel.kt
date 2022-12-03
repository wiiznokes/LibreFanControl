package ui.screen.body.tempList.tem

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.sensor.Temp

class AddTempViewModel(
    private val _tempList: MutableStateFlow<SnapshotStateList<Temp>> = State._tempList,
    private val _addTempList: MutableStateFlow<SnapshotStateList<Temp>> = State._addTempList
) {
    fun add(index: Int) {
        _addTempList.update {
            val tempBehavior = it.removeAt(index)
            _tempList.update { it2 ->
                _tempList.value.add(tempBehavior)
                it2
            }
            it
        }
    }
}