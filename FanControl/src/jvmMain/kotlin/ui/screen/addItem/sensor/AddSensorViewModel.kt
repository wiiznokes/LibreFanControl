package ui.screen.addItem.sensor

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.ItemType
import model.item.SensorItem
import ui.utils.Resources
import utils.getAvailableId
import utils.getAvailableName

class AddSensorViewModel(
    private val _fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._fanItemList,
    private val _tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._tempItemList,
) {
    fun addFan() {
        val name = getAvailableName(
            names = _fanItemList.value.map { item ->
                item.name
            },
            prefix = Resources.getString("default/fan_name")
        )

        _fanItemList.update {
            it.add(
                SensorItem(
                    name = name,
                    type = ItemType.SensorType.S_FAN,
                    sensorName = Resources.getString("none"),
                    sensorId = null,
                    itemId = getAvailableId(
                        ids = _fanItemList.value.map { item ->
                            item.itemId
                        }
                    )
                )
            )
            it
        }
    }

    fun addTemp() {
        val name = getAvailableName(
            names = _tempItemList.value.map { item ->
                item.name
            },
            prefix = Resources.getString("default/temp_name")
        )

        _tempItemList.update {
            it.add(
                SensorItem(
                    name = name,
                    type = ItemType.SensorType.S_TEMP,
                    sensorName = Resources.getString("none"),
                    sensorId = null,
                    itemId = getAvailableId(
                        ids = _tempItemList.value.map { item ->
                            item.itemId
                        }
                    )
                )
            )
            it
        }
    }
}