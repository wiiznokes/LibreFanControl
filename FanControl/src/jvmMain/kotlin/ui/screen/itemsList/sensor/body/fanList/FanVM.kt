package ui.screen.itemsList.sensor.body.fanList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Sensor
import model.item.sensor.Fan
import model.item.sensor.SensorItem
import utils.Name.Companion.checkNameTaken

class FanVM(
    val fanItemList: SnapshotStateList<SensorItem> = State.iFans,
    val fanList: SnapshotStateList<Sensor> = State.hFans
) {

    fun remove(index: Int) {
        fanItemList.removeAt(index)
    }

    fun setFan(index: Int, sensorId: Long?) {
        fanItemList[index] = fanItemList[index].copy(
            extension = (fanItemList[index].extension as Fan).copy(
                sensorId = sensorId
            )
        )
    }

    fun setName(name: String, index: Int) {
        checkNameTaken(
            names = fanItemList.map { item ->
                item.name
            },
            name = name,
            index = index
        )
        fanItemList[index] = fanItemList[index].copy(
            name = name
        )
    }
}