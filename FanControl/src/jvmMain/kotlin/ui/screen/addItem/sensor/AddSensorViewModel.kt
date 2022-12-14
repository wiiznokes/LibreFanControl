package ui.screen.addItem.sensor

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.ItemType
import model.item.SensorItem
import ui.utils.Resources
import ui.utils.getAvailableName

class AddSensorViewModel(
    private val _fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._fanItemList,
    private val _tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._tempItemList,
) {
    fun addFan() {
        val name = getAvailableName(
            list = _fanItemList.value,
            prefix = Resources.getString("default/fan_name")
        )

        _fanItemList.update {
            _fanItemList.value.add(
                SensorItem(
                    name = name,
                    type = ItemType.SensorType.FAN,
                    sensorName = Resources.getString("none"),
                    sensorId = null
                )
            )
            it
        }
    }

    fun addTemp() {
        val name = getAvailableName(
            list = _tempItemList.value,
            prefix = Resources.getString("default/temp_name")
        )

        _tempItemList.update {
            _tempItemList.value.add(
                SensorItem(
                    name = name,
                    type = ItemType.SensorType.TEMP,
                    sensorName = Resources.getString("none"),
                    sensorId = null
                )
            )
            it
        }
    }
}