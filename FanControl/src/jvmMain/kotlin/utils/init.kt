package utils

import SensorLists
import State
import State.hControls
import State.hSensorsList
import State.iControls
import State.iFans
import State.iTemps
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.item.control.ControlItem
import model.item.sensor.Fan
import model.item.sensor.SensorItem
import model.item.sensor.Temp
import utils.Id.Companion.getAvailableId


/**
 * init fan and temperature item list, used when there is no config at start.
 * Each sensor will be represented by one sensor item.
 */
fun initSensor() {
    hControls.forEach { hControl ->
        iControls.add(
            ControlItem(
                name = hControl.name,
                type = ItemType.ControlType.I_C_FAN,
                id = getAvailableId(
                    ids = iControls.map { it.id }
                )
            )
        )
    }

    hSensorsList.hFans.forEach { hFan ->
        iFans.add(
            SensorItem(
                name = hFan.name,
                type = ItemType.SensorType.I_S_FAN,
                id = getAvailableId(
                    ids = iFans.map { it.id }
                ),
                extension = Fan(hFan.id)
            )
        )
    }

    hSensorsList.hTemps.forEach { hTemp ->
        iTemps.add(
            SensorItem(
                name = hTemp.name,
                type = ItemType.SensorType.I_S_TEMP,
                id = getAvailableId(
                    ids = iTemps.map { it.id }
                ),
                extension = Temp(hTemp.id)
            )
        )
    }
}