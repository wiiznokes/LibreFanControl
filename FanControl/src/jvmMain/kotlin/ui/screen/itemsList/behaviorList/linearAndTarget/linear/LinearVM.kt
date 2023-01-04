package ui.screen.itemsList.behaviorList.linearAndTarget.linear

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Sensor
import model.item.behavior.Behavior
import model.item.behavior.Linear
import ui.screen.itemsList.behaviorList.linearAndTarget.LinAndTarParams
import ui.screen.itemsList.behaviorList.linearAndTarget.numberChoiceFinalValue


enum class LinearParams : LinAndTarParams {
    MIN_TEMP,
    MAX_TEMP,
    MIN_FAN_SPEED,
    MAX_FAN_SPEED
}


class LinearVM(
    private val behaviorList: SnapshotStateList<Behavior> = State.behaviorList,
    val tempList: SnapshotStateList<Sensor> = State.sensorLists.tempList
) {


    fun setTemp(index: Int, tempSensorId: Long?) {
        behaviorList[index] = behaviorList[index].copy(
            extension = (behaviorList[index].extension as Linear).copy(
                tempSensorId = tempSensorId
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
        val extension = with(behaviorList[index].extension as Linear) {
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
        behaviorList[index] = behaviorList[index].copy(
            extension = extension
        )
        return finalValue.toString()
    }
}