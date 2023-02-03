package utils

import SensorLists
import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.item.sensor.Fan
import model.item.sensor.SensorItem
import model.item.sensor.Temp
import utils.Id.Companion.getAvailableId


/**
 * init fan and temperature item list, used when there is no config at start.
 * Each sensor will be represented by one sensor item.
 */
fun initSensor(
    sensorLists: SensorLists = State.hSensorsList,

    fanItemList: SnapshotStateList<SensorItem> = State.iFans,
    tempItemList: SnapshotStateList<SensorItem> = State.iTemps
) {
    sensorLists.hFans.forEach { fanSensor ->
        fanItemList.add(
            SensorItem(
                name = fanSensor.libName,
                type = ItemType.SensorType.I_S_FAN,
                id = getAvailableId(
                    ids = fanItemList.map { it.id }
                ),
                extension = Fan(fanSensor.id)
            )
        )
    }

    sensorLists.hTemps.forEach { tempSensor ->
        tempItemList.add(
            SensorItem(
                name = tempSensor.libName,
                type = ItemType.SensorType.I_S_TEMP,
                id = getAvailableId(
                    ids = tempItemList.map { it.id }
                ),
                extension = Temp(tempSensor.id)
            )
        )
    }
}