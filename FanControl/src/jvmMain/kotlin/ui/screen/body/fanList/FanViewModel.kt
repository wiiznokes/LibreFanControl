package ui.screen.body.fanList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.Sensor
import model.item.SensorItem
import ui.utils.Resources
import utils.checkNameTaken

class FanViewModel(
    private val _fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._fanItemList,
    private val _fanList: MutableStateFlow<SnapshotStateList<Sensor>> = State._fanList
) {
    val fanList = _fanList.asStateFlow()
    val fanItemList = _fanItemList.asStateFlow()

    fun remove(index: Int) {
        _fanItemList.update {
            it.removeAt(index)
            it
        }
    }

    fun setFan(index: Int, fan: Sensor?) {
        if (fan != null) {
            _fanItemList.update {
                it[index] = it[index].copy(
                    sensorName = fan.libName,
                    sensorId = fan.libId
                )
                it
            }
        } else {
            _fanItemList.update {
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
            names = _fanItemList.value.map { item ->
                item.name
            },
            name = name,
            index = index
        )

        _fanItemList.update {
            it[index] = it[index].copy(
                name = name
            )
            it
        }
    }
}