package ui.screen.itemsList.behaviorList.linear

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Sensor
import model.item.behavior.BehaviorItem
import model.item.behavior.LinearBehavior


enum class LinearParams {
    MIN_TEMP,
    MAX_TEMP,
    MIN_FAN_SPEED,
    MAX_FAN_SPEED
}

private fun getFinalValue(value: Int): Int =
    if (value < 0)
        0
    else {
        if (value > 100)
            100
        else
            value
    }


class LinearVM(
    private val behaviorItemList: SnapshotStateList<BehaviorItem> = State.behaviorItemList,
    val tempList: SnapshotStateList<Sensor> = State.sensorLists.tempList
) {


    fun setTemp(index: Int, tempSensorId: Long?) {
        behaviorItemList[index] = behaviorItemList[index].copy(
            extension = (behaviorItemList[index].extension as LinearBehavior).copy(
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
        val extension = with(behaviorItemList[index].extension as LinearBehavior) {
            when (type) {
                LinearParams.MIN_TEMP -> {
                    finalValue = getFinalValue(value(minTemp))
                    copy(minTemp = finalValue)
                }

                LinearParams.MAX_TEMP -> {
                    finalValue = getFinalValue(value(maxTemp))
                    copy(maxTemp = finalValue)
                }

                LinearParams.MIN_FAN_SPEED -> {
                    finalValue = getFinalValue(value(minFanSpeed))
                    copy(minFanSpeed = finalValue)
                }

                LinearParams.MAX_FAN_SPEED -> {
                    finalValue = getFinalValue(value(maxFanSpeed))
                    copy(maxFanSpeed = finalValue)
                }
            }
        }
        behaviorItemList[index] = behaviorItemList[index].copy(
            extension = extension
        )
        return finalValue.toString()
    }
}