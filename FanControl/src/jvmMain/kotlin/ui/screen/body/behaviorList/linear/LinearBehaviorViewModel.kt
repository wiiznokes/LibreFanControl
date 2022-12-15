package ui.screen.body.behaviorList.linear

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.item.behavior.BehaviorItem


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
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList
) {

    fun increase(index: Int, type: LinearParams): String {
        var finalValue = 0

        _behaviorItemList.update {
            _behaviorItemList.value[index] = _behaviorItemList.value[index].copy(
                linearBehavior = with(_behaviorItemList.value[index].linearBehavior!!) {
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
            _behaviorItemList.value[index] = _behaviorItemList.value[index].copy(
                linearBehavior = with(_behaviorItemList.value[index].linearBehavior!!) {
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
            _behaviorItemList.value[index] = _behaviorItemList.value[index].copy(
                linearBehavior = with(_behaviorItemList.value[index].linearBehavior!!) {
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