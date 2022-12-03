package ui.screen.body.fanList.fan

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.sensor.Fan

class AddFanViewModel(
    private val _fanList: MutableStateFlow<SnapshotStateList<Fan>> = State._fanList,
    private val _addFanList: MutableStateFlow<SnapshotStateList<Fan>> = State._addFanList
) {
    fun add(index: Int) {
        _addFanList.update {
            val tempBehavior = it.removeAt(index)
            _fanList.update { it2 ->
                _fanList.value.add(tempBehavior)
                it2
            }
            it
        }
    }
}