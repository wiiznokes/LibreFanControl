package ui.screen.body.behaviorList.linear

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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


class LinearBehaviorViewModel(
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList,
    private val _tempList: MutableStateFlow<SnapshotStateList<Sensor>> = State._tempList
) {

    val tempList = _tempList.asStateFlow()


    fun setTemp(index: Int, tempSensorId: Long?) {
        _behaviorItemList.update {
            it[index] = it[index].copy(
                extension = (it[index].extension as LinearBehavior).copy(
                    tempSensorId = tempSensorId
                )
            )
            it
        }
    }

    fun increase(index: Int, type: LinearParams): String {
        var finalValue = 0

        _behaviorItemList.update {
            it[index] = it[index].copy(
                extension = with(it[index].extension as LinearBehavior) {
                    when (type) {
                        LinearParams.MIN_TEMP -> {
                            finalValue = getFinalValue(minTemp + 1)
                            copy(
                                minTemp = finalValue
                            )
                        }

                        LinearParams.MAX_TEMP -> {
                            finalValue = getFinalValue(maxTemp + 1)
                            copy(
                                maxTemp = finalValue
                            )
                        }

                        LinearParams.MIN_FAN_SPEED -> {
                            finalValue = getFinalValue(minFanSpeed + 1)
                            copy(
                                minFanSpeed = finalValue
                            )
                        }

                        LinearParams.MAX_FAN_SPEED -> {
                            finalValue = getFinalValue(maxFanSpeed + 1)
                            copy(
                                maxFanSpeed = finalValue
                            )
                        }
                    }
                }
            )
            it
        }
        return finalValue.toString()
    }

    fun decrease(index: Int, type: LinearParams): String {
        var finalValue = 0

        _behaviorItemList.update {
            it[index] = it[index].copy(
                extension = with(it[index].extension as LinearBehavior) {
                    when (type) {
                        LinearParams.MIN_TEMP -> {
                            finalValue = getFinalValue(minTemp - 1)
                            copy(
                                minTemp = finalValue
                            )
                        }

                        LinearParams.MAX_TEMP -> {
                            finalValue = getFinalValue(maxTemp - 1)
                            copy(
                                maxTemp = finalValue
                            )
                        }

                        LinearParams.MIN_FAN_SPEED -> {
                            finalValue = getFinalValue(minFanSpeed - 1)
                            copy(
                                minFanSpeed = finalValue
                            )
                        }

                        LinearParams.MAX_FAN_SPEED -> {
                            finalValue = getFinalValue(maxFanSpeed - 1)
                            copy(
                                maxFanSpeed = finalValue
                            )
                        }
                    }
                }
            )
            it
        }
        return finalValue.toString()
    }

    fun onChange(index: Int, value: Int, type: LinearParams): String {
        val finalValue = getFinalValue(value)

        _behaviorItemList.update {
            it[index] = it[index].copy(
                extension = with(it[index].extension as LinearBehavior) {
                    when (type) {
                        LinearParams.MIN_TEMP -> {
                            copy(
                                minTemp = finalValue
                            )
                        }

                        LinearParams.MAX_TEMP -> {
                            copy(
                                maxTemp = finalValue
                            )
                        }

                        LinearParams.MIN_FAN_SPEED -> {
                            copy(
                                minFanSpeed = finalValue
                            )
                        }

                        LinearParams.MAX_FAN_SPEED -> {
                            copy(
                                maxFanSpeed = finalValue
                            )
                        }
                    }
                }
            )
            it
        }
        return finalValue.toString()
    }
}