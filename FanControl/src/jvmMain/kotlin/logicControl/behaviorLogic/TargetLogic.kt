package logicControl.behaviorLogic

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Sensor
import model.item.behavior.Behavior
import model.item.behavior.Target

class TargetLogic(
    private val behaviorList: SnapshotStateList<Behavior> = State.behaviorList,
    private val tempList: SnapshotStateList<Sensor> = State.sensorLists.tempList,
) {
    fun getValueAndChangeVariable(target: Target, index: Int): Int? {
        val value = getValue(target) ?: return null
        changeIdleHasBeenReach(target, index, value)
        return value
    }


    private fun changeIdleHasBeenReach(target: Target, index: Int, value: Int) {
        when (target.idleHasBeenReach) {
            true -> {
                if (value != target.idleFanSpeed) {
                    behaviorList[index] = behaviorList[index].copy(
                        extension = target.copy(
                            idleHasBeenReach = false
                        )
                    )
                }
            }

            false -> {
                if (value != target.loadFanSpeed) {
                    behaviorList[index] = behaviorList[index].copy(
                        extension = target.copy(
                            idleHasBeenReach = true
                        )
                    )
                }
            }
        }
    }


    fun getValue(target: Target): Int? {
        val tempValue = getTempValue(target.tempSensorId, tempList) ?: return null

        return when (target.idleHasBeenReach) {
            true -> {
                if (tempValue < target.loadTemp) {
                    target.idleFanSpeed
                } else {
                    target.loadFanSpeed
                }
            }

            false -> {
                if (tempValue <= target.idleTemp) {
                    target.idleFanSpeed
                } else {
                    target.loadFanSpeed
                }
            }
        }
    }
}