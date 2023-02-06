package ui.screen.itemsList.sensor.body.tempList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.hardware.Sensor
import model.item.behavior.Behavior
import model.item.sensor.CustomTemp
import model.item.sensor.CustomTempType
import model.item.sensor.SensorI
import model.item.sensor.Temp
import utils.Name.Companion.checkNameTaken

class TempVM(
    val iTemps: SnapshotStateList<SensorI> = State.iTemps,
    val hTemps: SnapshotStateList<Sensor> = State.hTemps,
    val iBehaviors: SnapshotStateList<Behavior> = State.iBehaviors
) {


    private data class BehaviorInfo(
        val index: Int,
        val type: ItemType.BehaviorType
    )


    fun remove(index: Int) {

        val itemp = iTemps[index]

        /**
         * only for custom sensor.
         * we need to remove id in behaviors if necessary
         */
        if (itemp.id < 0) {
            for (i in iBehaviors.indices) {
                val behavior = iBehaviors[i]
                when (behavior.type) {
                    ItemType.BehaviorType.I_B_LINEAR -> {
                        if ((behavior.extension as Linear).hTempId == itemp.id) {
                            iBehaviors[i] = behavior.copy(
                                extension = behavior.extension.copy(
                                    hTempId = null
                                )
                            )
                        }
                    }

                    ItemType.BehaviorType.I_B_TARGET -> {
                        if ((behavior.extension as Target).hTempId == itemp.id) {
                            iBehaviors[i] = behavior.copy(
                                extension = behavior.extension.copy(
                                    hTempId = null
                                )
                            )
                        }
                    }

                    else -> {}
                }
            }
        }


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