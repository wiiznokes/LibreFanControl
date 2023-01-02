package ui.screen.body.fanList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Sensor
import model.item.SensorItem
import utils.checkNameTaken

class FanViewModel(
    val fanItemList: SnapshotStateList<SensorItem> = State.fanItemList,
    val fanList: SnapshotStateList<Sensor> = State.sensorLists.fanList
) {

    fun remove(index: Int) {
        fanItemList.removeAt(index)
    }

    fun setFan(index: Int, sensorId: Long?) {
        fanItemList[index].sensorId = sensorId
    }

    fun setName(name: String, index: Int) {
        checkNameTaken(
            names = fanItemList.map { item ->
                item.name
            },
            name = name,
            index = index
        )
        fanItemList[index].name = name
    }
}