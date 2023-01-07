package ui.screen.itemsList.sensor.body.tempList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Sensor
import model.item.sensor.CustomTemp
import model.item.sensor.CustomTempType
import model.item.sensor.SensorItem
import model.item.sensor.Temp
import utils.Name.Companion.checkNameTaken

class TempVM(
    val tempItemList: SnapshotStateList<SensorItem> = State.tempItemList,
    val tempList: SnapshotStateList<Sensor> = State.sensorLists.tempList
) {


    fun remove(index: Int) {
        tempItemList.removeAt(index)
    }

    fun setTemp(index: Int, sensorId: Long?) {
        tempItemList[index] = tempItemList[index].copy(
            extension = (tempItemList[index].extension as Temp).copy(
                sensorId = sensorId
            )
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

    fun setCustomType(type: CustomTempType, index: Int) {
        tempItemList[index] = tempItemList[index].copy(
            extension = (tempItemList[index].extension as CustomTemp).copy(
                type = type
            )
        )
    }

    fun addTempCustom(id: Long, index: Int) {
        (tempItemList[index].extension as CustomTemp).sensorIdList.add(id)
    }

    fun removeTempCustom(id: Long, index: Int) {
        (tempItemList[index].extension as CustomTemp).sensorIdList.remove(id)
    }
}