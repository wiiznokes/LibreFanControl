package ui.screen.addItem.sensor

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.ItemType
import model.item.SensorItem
import ui.utils.Resources

class AddSensorViewModel(
    private val _fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._fanItemList,
    private val _tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._tempItemList,
) {
    fun addFan() {
        _fanItemList.update {
            _fanItemList.value.add(
                SensorItem(
                    name = "fan1",
                    type = ItemType.SensorType.FAN,
                    sensorName = Resources.getString("none"),
                    sensorId = null
                )
            )
            it
        }
    }

    fun addTemp() {
        _tempItemList.update {
            _tempItemList.value.add(
                SensorItem(
                    name = "temp1",
                    type = ItemType.SensorType.TEMP,
                    sensorName = Resources.getString("none"),
                    sensorId = null
                )
            )
            it
        }
    }
}