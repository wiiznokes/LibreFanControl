package utils

import State.hControls
import State.hFans
import State.hTemps
import State.iControls
import State.iFans
import State.iTemps
import model.ItemType
import model.item.control.IControl
import model.item.sensor.*
import utils.Id.Companion.getAvailableId


/**
 * init fan and temperature item list, used when there is no config at start.
 * Each sensor will be represented by one sensor item.
 */
fun initSensor() {
    hControls.forEach { hControl ->
        iControls.add(
            IControl(
                name = hControl.name,
                id = getAvailableId(
                    ids = iControls.map { it.id },
                    prefix = "control"
                ),
                controlId = hControl.id
            )
        )
    }

    hFans.forEach { hFan ->
        iFans.add(
            IFan(
                name = hFan.name,
                id = getAvailableId(
                    ids = iFans.map { it.id },
                    prefix = "fan"
                ),
                hFanId = hFan.id
            )
        )
    }

    hTemps.forEach { hTemp ->
        iTemps.add(
            ITemp(
                name = hTemp.name,
                id = getAvailableId(
                    ids = iTemps.map { it.id },
                    prefix = "temp"
                ),
                hTempId = hTemp.id
            )
        )
    }
}