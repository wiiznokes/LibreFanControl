package ui.screen.itemsList.behaviorList.linearAndTarget.target

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Sensor
import model.item.behavior.Behavior
import model.item.behavior.Target
import ui.screen.itemsList.behaviorList.linearAndTarget.LinAndTarParams
import ui.screen.itemsList.behaviorList.linearAndTarget.numberChoiceFinalValue


enum class TargetParams : LinAndTarParams {
    IDLE_TEMP,
    LOAD_TEMP,
    IDLE_FAN_SPEED,
    LOAD_FAN_SPEED
}

class TargetVM(
    private val behaviorList: SnapshotStateList<Behavior> = State.behaviorList,
    val tempList: SnapshotStateList<Sensor> = State.sensorLists.tempList
) {

    fun setTemp(index: Int, tempSensorId: Long?) {
        behaviorList[index] = behaviorList[index].copy(
            extension = (behaviorList[index].extension as Target).copy(
                tempSensorId = tempSensorId
            )
        )
    }

    fun increase(index: Int, type: TargetParams): String {
        return updateValue(index, type) {
            it + 1
        }
    }

    fun decrease(index: Int, type: TargetParams): String {
        return updateValue(index, type) {
            it - 1
        }
    }

    fun onChange(index: Int, value: Int, type: TargetParams): String {
        return updateValue(index, type) {
            value
        }
    }


    /**
     * @param value used to make operation on the previous value before calculation of the final value
     */
    private fun updateValue(index: Int, type: TargetParams, value: (Int) -> Int): String {
        val finalValue: Int
        val extension = with(behaviorList[index].extension as Target) {
            when (type) {

                TargetParams.IDLE_TEMP -> {
                    finalValue = numberChoiceFinalValue(value(idleTemp))
                    copy(idleTemp = finalValue)
                }

                TargetParams.LOAD_TEMP -> {
                    finalValue = numberChoiceFinalValue(value(loadTemp))
                    copy(loadTemp = finalValue)
                }

                TargetParams.IDLE_FAN_SPEED -> {
                    finalValue = numberChoiceFinalValue(value(idleFanSpeed))
                    copy(idleFanSpeed = finalValue)
                }

                TargetParams.LOAD_FAN_SPEED -> {
                    finalValue = numberChoiceFinalValue(value(loadFanSpeed))
                    copy(loadFanSpeed = finalValue)
                }
            }
        }
        behaviorList[index] = behaviorList[index].copy(
            extension = extension
        )
        return finalValue.toString()
    }
}