package ui.screen.itemsList.sensor.body.tempList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.hardware.Sensor
import model.item.behavior.Behavior
import model.item.behavior.Linear
import model.item.behavior.Target
import model.item.sensor.CustomTemp
import model.item.sensor.CustomTempType
import model.item.sensor.SensorItem
import model.item.sensor.Temp
import utils.Name.Companion.checkNameTaken
import utils.getIndexList

class TempVM(
    val iTemps: SnapshotStateList<SensorItem> = State.iTemps,
    val hTemps: SnapshotStateList<Sensor> = State.hTemps,
    val iBehaviors: SnapshotStateList<Behavior> = State.iBehaviors
) {


    fun remove(index: Int) {

        val itemp = iTemps[index]

        getIndexList(
            list = iBehaviors,
            predicate = {
                when (it.type) {
                    ItemType.BehaviorType.I_B_FLAT -> {
                        false
                    }
                    ItemType.BehaviorType.I_B_LINEAR -> {
                        (it.extension as Linear).hTempId == itemp.id
                    }
                    ItemType.BehaviorType.I_B_TARGET -> {
                        (it.extension as Target).hTempId == itemp.id
                    }
                }
            }
        )

        iTemps.removeAt(index)
    }

    fun setTemp(index: Int, sensorId: Long?) {
        iTemps[index] = iTemps[index].copy(
            extension = (iTemps[index].extension as Temp).copy(
                hTempId = sensorId
            )
        )
    }


    fun setName(name: String, index: Int) {
        checkNameTaken(
            names = iTemps.map { item ->
                item.name
            },
            name = name,
            index = index
        )
        iTemps[index] = iTemps[index].copy(
            name = name
        )
    }

    fun setCustomType(type: CustomTempType, index: Int) {
        iTemps[index] = iTemps[index].copy(
            extension = (iTemps[index].extension as CustomTemp).copy(
                customTempType = type
            )
        )
    }

    fun addTempCustom(id: Long, index: Int) {
        (iTemps[index].extension as CustomTemp).sensorIdList.add(id)
    }

    fun removeTempCustom(id: Long, index: Int) {
        (iTemps[index].extension as CustomTemp).sensorIdList.remove(id)
    }
}