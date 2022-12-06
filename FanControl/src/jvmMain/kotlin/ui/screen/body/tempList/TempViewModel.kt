package ui.screen.body.tempList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.Sensor
import model.item.SensorItem

class TempViewModel(
    private val _tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._tempItemList,
    private val _tempList: MutableStateFlow<SnapshotStateList<Sensor>> = State._tempList
) {
    val tempList = _tempList.asStateFlow()
    val tempItemList = _tempItemList.asStateFlow()


    fun remove(index: Int) {
        _tempItemList.update {
            _tempItemList.value.removeAt(index)
            it
        }
    }

    fun changeSensorId(index: Int, menuIndex: Int) {
        _tempItemList.update {
            _tempItemList.value[index] = _tempItemList.value[index].copy(
                sensorName = _tempList.value[menuIndex].libName
            )
            it
        }
    }


    fun setName(name: String, index: Int) {

        _tempItemList.update {
            _tempItemList.value[index] = _tempItemList.value[index].copy(
                name = name
            )
            it
        }
    }
}