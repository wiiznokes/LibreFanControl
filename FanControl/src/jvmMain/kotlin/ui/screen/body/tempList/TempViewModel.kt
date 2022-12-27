package ui.screen.body.tempList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.Sensor
import model.item.SensorItem
import ui.utils.Resources
import ui.utils.checkNameTaken

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

    fun setTemp(index: Int, temp: Sensor?) {
        if (temp != null) {
            _tempItemList.update {
                it[index] = it[index].copy(
                    sensorName = temp.libName,
                    sensorId = temp.libId
                )
                it
            }
        } else {
            _tempItemList.update {
                it[index] = it[index].copy(
                    sensorName = Resources.getString("none"),
                    sensorId = null
                )
                it
            }
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