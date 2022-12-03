package ui.screen.body.fanList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.sensor.Fan

class FanViewModel(
    private val _fanList: MutableStateFlow<SnapshotStateList<Fan>> = State._fanList,
    private val _addFanList: MutableStateFlow<SnapshotStateList<Fan>> = State._addFanList
) {
    fun remove(index: Int) {
        _fanList.update {
            val tempFan = it.removeAt(index)
            _addFanList.update { it2 ->
                _addFanList.value.add(tempFan)
                it2
            }
            it
        }
    }


    fun setName(name: String, index: Int) {

        _fanList.update {
            _fanList.value[index] = _fanList.value[index].copy(
                name = name
            )
            it
        }
    }
}