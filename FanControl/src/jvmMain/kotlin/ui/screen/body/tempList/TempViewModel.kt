package ui.screen.body.tempList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Sensor
import model.item.SensorItem
import utils.checkNameTaken

class TempViewModel(
    val tempItemList: SnapshotStateList<SensorItem> = State.tempItemList,
    val tempList: SnapshotStateList<Sensor> = State.sensorLists.tempList
) {


    fun remove(index: Int) {
        tempItemList.removeAt(index)
    }

    fun setTemp(index: Int, sensorId: Long?) {
        tempItemList[index] = tempItemList[index].copy(
            sensorId = sensorId
        )
    }


    fun setName(name: String, index: Int) {
        checkNameTaken(
            names = tempItemList.map { item ->
                item.name
            },
            name = name,
            index = index
        )
        tempItemList[index] = tempItemList[index].copy(
            name = name
        )
    }
}