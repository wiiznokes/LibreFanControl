package ui.screen.body.tempList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.sensor.Temp

class TempViewModel(
    private val _tempList: MutableStateFlow<SnapshotStateList<Temp>> = State._tempList,
    private val _addTempList: MutableStateFlow<SnapshotStateList<Temp>> = State._addTempList
) {

    val tempList = State._tempList.asStateFlow()

    fun remove(index: Int) {
        _tempList.update {
            val tempBehavior = it.removeAt(index)
            _addTempList.update { it2 ->
                _addTempList.value.add(tempBehavior)
                it2
            }
            it
        }
    }


    fun setName(name: String, index: Int) {

        _tempList.update {
            _tempList.value[index] = _tempList.value[index].copy(
                name = name
            )
            it
        }
    }
}