package utils

import State.hControls
import State.hFans
import State.hTemps
import State.iBehaviors
import State.iControls
import State.iFans
import State.iTemps
import model.ItemType
import model.item.behavior.Behavior
import model.item.behavior.Flat
import model.item.behavior.Linear
import model.item.behavior.Target
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
                ),
                controlId = hControl.id
            )
        )
    }

    hFans.forEach { hFan ->
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

    hTemps.forEach { hTemp ->
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


    iBehaviors.add(
        Behavior(
            name = "flat",
            id = 0,
            type = ItemType.BehaviorType.I_B_FLAT,
            extension = Flat()
        )
    )
    iBehaviors.add(
        Behavior(
            name = "linear",
            id = 1,
            type = ItemType.BehaviorType.I_B_LINEAR,
            extension = Linear()
        )
    )
    iBehaviors.add(
        Behavior(
            name = "target",
            id = 2,
            type = ItemType.BehaviorType.I_B_TARGET,
            extension = Target()
        )
    )
}