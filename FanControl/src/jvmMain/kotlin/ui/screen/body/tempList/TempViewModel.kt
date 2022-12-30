package ui.screen.body.tempList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.Sensor
import model.item.SensorItem
import ui.utils.Resources
import utils.checkNameTaken

class TempViewModel(
    private val _tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._tempItemList,
    private val _tempList: MutableStateFlow<SnapshotStateList<Sensor>> = State._tempList
) {
    val tempList = _tempList.asStateFlow()
    val tempItemList = _tempItemList.asStateFlow()


    fun remove(index: Int) {
        _tempItemList.update {
            it.removeAt(index)
            it
        }
    }

    fun setTemp(index: Int, sensorId: Long?) {
        _tempItemList.update {
            it[index] = it[index].copy(
                sensorId = sensorId,
            )
            it
        }
    }


    fun setName(name: String, index: Int) {

        checkNameTaken(
            names = _tempItemList.value.map { item ->
                item.name
            },
            name = name,
            index = index,
        )

        _tempItemList.update {
            it[index] = it[index].copy(
                name = name
            )
            it
        }
    }
}