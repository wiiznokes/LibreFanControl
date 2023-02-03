package ui.screen.itemsList.behaviorList.linearAndTarget.target


import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.hardware.Sensor
import model.item.behavior.Behavior
import model.item.behavior.Target
import model.item.sensor.SensorItem
import ui.screen.itemsList.behaviorList.BaseBehaviorVM
import ui.screen.itemsList.behaviorList.linearAndTarget.LinAndTarParams
import ui.screen.itemsList.behaviorList.linearAndTarget.numberChoiceFinalValue
import ui.utils.Resources
import utils.Id
import utils.Name


enum class TargetParams : LinAndTarParams {
    IDLE_TEMP,
    LOAD_TEMP,
    IDLE_FAN_SPEED,
    LOAD_FAN_SPEED
}

class TargetVM(
    val tempList: SnapshotStateList<Sensor> = State.hSensorsList.hTemps,
    val tempItemList: SnapshotStateList<SensorItem> = State.iTemps
) : BaseBehaviorVM() {

    fun setTemp(index: Int, tempSensorId: Long?) {
        updateSafely(
            index = index,
            behaviorOperation = {
                behaviorList[index] = behaviorList[index].copy(
                    extension = (behaviorList[index].extension as Target).copy(
                        tempSensorId = tempSensorId
                    )
                )
            }
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


    fun defaultTarget() = Behavior(
        name = Name.getAvailableName(
            names = behaviorList.map { item ->
                item.name
            },
            prefix = Resources.getString("default/target_name")
        ),
        type = ItemType.BehaviorType.I_B_TARGET,
        extension = Target(),
        id = Id.getAvailableId(
            ids = behaviorList.map { item ->
                item.id
            }
        )
    )
}