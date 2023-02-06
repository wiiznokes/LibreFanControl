package ui.screen.itemsList.sensor.body.fanList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Sensor
import model.item.sensor.BaseIFan
import model.item.sensor.SensorI
import utils.Name.Companion.checkNameTaken

class FanVM(
    val iFans: SnapshotStateList<SensorI> = State.iFans,
    val hFans: SnapshotStateList<Sensor> = State.hFans
) {

    fun remove(index: Int) {
        iFans.removeAt(index)
    }

    fun setFan(index: Int, sensorId: Long?) {
        iFans[index] = iFans[index].copy(
            extension = (iFans[index].extension as BaseIFan).copy(
                hFanId = sensorId
            )
        )
    }

    fun setName(name: String, index: Int) {
        checkNameTaken(
            names = iFans.map { item ->
                item.name
            },
            name = name,
            index = index
        )
        iFans[index] = iFans[index].copy(
            name = name
        )
    }
}