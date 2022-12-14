package ui.screen.body.fanList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.Sensor
import model.item.SensorItem
import ui.utils.Resources
import ui.utils.checkNameTaken

class FanViewModel(
    private val _fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._fanItemList,
    private val _fanList: MutableStateFlow<SnapshotStateList<Sensor>> = State._fanList
) {
    val fanList = _fanList.asStateFlow()
    val fanItemList = _fanItemList.asStateFlow()

    fun remove(index: Int) {
        _fanItemList.update {
            _fanItemList.value.removeAt(index)
            it
        }
    }

    fun setFan(index: Int, fan: Sensor?) {
        if (fan != null) {
            _fanItemList.update {
                _fanItemList.value[index] = _fanItemList.value[index].copy(
                    sensorName = fan.libName,
                    sensorId = fan.libId
                )
                it
            }
        } else {
            _fanItemList.update {
                _fanItemList.value[index] = _fanItemList.value[index].copy(
                    sensorName = Resources.getString("none"),
                    sensorId = null
                )
                it
            }
        }
    }

    fun setName(name: String, index: Int) {
        checkNameTaken(_fanItemList.value, name, index)

        _fanItemList.update {
            _fanItemList.value[index] = _fanItemList.value[index].copy(
                name = name
            )
            it
        }
    }
}