package helper

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.ItemType
import model.hardware.Sensor
import model.item.SensorItem
import settings.Settings
import utils.getAvailableId


// returns configId if it exists, otherwise null
fun checkConfig(): Long? {
    return Settings.getSetting("config")
}


// init sensor list, used when there is no config at start
fun initSensor(
    fanList: MutableStateFlow<SnapshotStateList<Sensor>> = State._fanList,
    tempList: MutableStateFlow<SnapshotStateList<Sensor>> = State._tempList,

    fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._fanItemList,
    tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._tempItemList
) {
    fanList.value.forEach {
        fanItemList.value.add(
            SensorItem(
                name = it.libName,
                type = ItemType.SensorType.S_FAN,
                sensorName = it.libName,
                libId = it.libId,
                itemId = getAvailableId(
                    ids = fanItemList.value.map { item ->
                        item.itemId
                    }
                )
            )
        )
    }

    tempList.value.forEach {
        tempItemList.value.add(
            SensorItem(
                name = it.libName,
                type = ItemType.SensorType.S_TEMP,
                sensorName = it.libName,
                libId = it.libId,
                itemId = getAvailableId(
                    ids = tempItemList.value.map { item ->
                        item.itemId
                    }
                )
            )
        )
    }
}