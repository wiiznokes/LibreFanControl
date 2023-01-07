package logicControl.behavior

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import logicControl.temp.TempLogic
import model.item.behavior.Behavior
import model.item.behavior.Target

class TargetLogic(
    private val behaviorList: SnapshotStateList<Behavior> = State.behaviorList
) {

    fun getValue(target: Target): Int? {
        val tempValue = TempLogic.getValue(target.tempSensorId) ?: return null

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
}