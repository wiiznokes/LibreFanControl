package ui.screen.itemsList.behaviorList.linearAndTarget.linear

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.hardware.Sensor
import model.item.behavior.Behavior
import model.item.sensor.SensorI
import ui.screen.itemsList.behaviorList.BaseBehaviorVM
import ui.screen.itemsList.behaviorList.linearAndTarget.LinAndTarParams
import ui.screen.itemsList.behaviorList.linearAndTarget.numberChoiceFinalValue
import ui.utils.Resources
import utils.Id
import utils.Name


enum class LinearParams : LinAndTarParams {
    MIN_TEMP,
    MAX_TEMP,
    MIN_FAN_SPEED,
    MAX_FAN_SPEED
}


class LinearVM(
    val hTemps: SnapshotStateList<Sensor> = State.hTemps,
    val iTemps: SnapshotStateList<SensorI> = State.iTemps,
) : BaseBehaviorVM() {


    fun setTemp(index: Int, tempSensorId: Long?) {
        iBehaviors[index] = iBehaviors[index].copy(
            extension = (iBehaviors[index].extension as Linear).copy(
                hTempId = tempSensorId
            )
        )
    }

    fun increase(index: Int, type: LinearParams): String {
        return updateValue(index, type) {
            it + 1
        }
    }

    fun decrease(index: Int, type: LinearParams): String {
        return updateValue(index, type) {
            it - 1
        }
    }

    fun onChange(index: Int, value: Int, type: LinearParams): String {
        return updateValue(index, type) {
            value
        }
    }


    /**
     * @param value used to make operation on the previous value before calculation of the final value
     */
    private fun updateValue(index: Int, type: LinearParams, value: (Int) -> Int): String {
        val finalValue: Int
        val extension = with(iBehaviors[index].extension as Linear) {
            when (type) {
                LinearParams.MIN_TEMP -> {
                    finalValue = numberChoiceFinalValue(value(minTemp))
                    copy(minTemp = finalValue)
                }

                LinearParams.MAX_TEMP -> {
                    finalValue = numberChoiceFinalValue(value(maxTemp))
                    copy(maxTemp = finalValue)
                }

                LinearParams.MIN_FAN_SPEED -> {
                    finalValue = numberChoiceFinalValue(value(minFanSpeed))
                    copy(minFanSpeed = finalValue)
                }

                LinearParams.MAX_FAN_SPEED -> {
                    finalValue = numberChoiceFinalValue(value(maxFanSpeed))
                    copy(maxFanSpeed = finalValue)
                }
            }
        }
        iBehaviors[index] = iBehaviors[index].copy(
            extension = extension
        )
        return finalValue.toString()
    }

    fun defaultLinear() = Behavior(
        name = Name.getAvailableName(
            names = iBehaviors.map { item ->
                item.name
            },
            prefix = Resources.getString("default/linear_name")
        ),
        type = ItemType.BehaviorType.I_B_LINEAR,
        extension = Linear(),
        id = Id.getAvailableId(
            ids = iBehaviors.map { item ->
                item.id
            }
        )
    )
}