package utils

import SensorLists
import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.item.SensorItem

//
/**
 * init fan and temperature item list, used when there is no config at start.
 * Each sensor will be represented by one sensor item.
 */
fun initSensor(
    sensorLists: SensorLists = State.sensorLists,

    fanItemList: SnapshotStateList<SensorItem> = State.fanItemList,
    tempItemList: SnapshotStateList<SensorItem> = State.tempItemList
) {
    sensorLists.fanList.forEach { fanSensor ->
        fanItemList.add(
            SensorItem(
                name = fanSensor.libName,
                type = ItemType.SensorType.I_S_FAN,
                itemId = getAvailableId(
                    ids = fanItemList.map { it.itemId }
                ),
                sensorId = fanSensor.id
            )
        )
    }

    sensorLists.tempList.forEach { tempSensor ->
        tempItemList.add(
            SensorItem(
                name = tempSensor.libName,
                type = ItemType.SensorType.I_S_FAN,
                itemId = getAvailableId(
                    ids = tempItemList.map { it.itemId }
                ),
                sensorId = tempSensor.id
            )
        )
    }
}