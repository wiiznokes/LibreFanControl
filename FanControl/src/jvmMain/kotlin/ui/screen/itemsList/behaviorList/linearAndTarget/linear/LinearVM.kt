package ui.screen.itemsList.behaviorList.linearAndTarget.linear

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.hardware.Sensor
import model.item.behavior.Behavior
import model.item.behavior.Linear
import model.item.sensor.SensorItem
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
    val tempList: SnapshotStateList<Sensor> = State.sensorLists.tempList,
    val tempItemList: SnapshotStateList<SensorItem> = State.tempItemList,
) : BaseBehaviorVM() {


    fun setTemp(index: Int, tempSensorId: Long?) {
        updateSafely(
            index = index,
            behaviorOperation = {
                behaviorList[index] = behaviorList[index].copy(
                    extension = (behaviorList[index].extension as Linear).copy(
                        tempSensorId = tempSensorId
                    )
                )
            }
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

    fun defaultLinear() = Behavior(
        name = Name.getAvailableName(
            names = behaviorList.map { item ->
                item.name
            },
            prefix = Resources.getString("default/linear_name")
        ),
        type = ItemType.BehaviorType.I_B_LINEAR,
        extension = Linear(),
        id = Id.getAvailableId(
            ids = behaviorList.map { item ->
                item.id
            }
        )
    )
}