package utils

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.ItemType
import model.hardware.Sensor
import model.item.SensorItem

// init sensor list, used when there is no config at start
fun initSensor(
    fanList: MutableStateFlow<SnapshotStateList<Sensor>> = State._fanList,
    tempList: MutableStateFlow<SnapshotStateList<Sensor>> = State._tempList,

    fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._fanItemList,
    tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._tempItemList
) {
    fanList.value.forEach { fanSensor ->
        fanItemList.update {
            it.add(
                SensorItem(
                    name = fanSensor.libName,
                    type = ItemType.SensorType.I_S_FAN,
                    itemId = getAvailableId(
                        ids = it.map { item -> item.itemId }
                    ),
                    sensorId = fanSensor.id
                )
            )
            it
        }
    }

    tempList.value.forEach { tempSensor ->
        tempItemList.update {
            it.add(
                SensorItem(
                    name = tempSensor.libName,
                    type = ItemType.SensorType.I_S_FAN,
                    itemId = getAvailableId(
                        ids = it.map { item -> item.itemId }
                    ),
                    sensorId = tempSensor.id
                )
            )
            it
        }
    }
}