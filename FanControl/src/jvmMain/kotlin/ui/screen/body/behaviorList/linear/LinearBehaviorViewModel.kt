package ui.screen.body.behaviorList.linear

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.item.behavior.BehaviorItem
import java.lang.Exception


enum class LinearParams {
    MIN_TEMP,
    MAX_TEMP,
    MIN_FAN_SPEED,
    MAX_FAN_SPEED
}

private fun getFinalValue(value: Int): Int =
    if(value < 0)
        0
    else {
        if(value > 100)
            100
        else
            value
    }


class LinearBehaviorViewModel(
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList
) {

    fun increase(index: Int, value: Int, type: LinearParams): Int {
        val finalValue = getFinalValue(value)

        _behaviorItemList.update {
            _behaviorItemList.value[index] = _behaviorItemList.value[index].copy(
                linearBehavior = with(_behaviorItemList.value[index].linearBehavior!!) {
                    when (type) {
                        LinearParams.MIN_TEMP -> {
                            copy(
                                minTemp = finalValue + 1
                            )
                        }

                        LinearParams.MAX_TEMP -> {
                            copy(
                                maxTemp = finalValue + 1
                            )
                        }

                        LinearParams.MIN_FAN_SPEED -> {
                            copy(
                                minFanSpeed = finalValue + 1
                            )
                        }

                        LinearParams.MAX_FAN_SPEED -> {
                            copy(
                                maxFanSpeed = finalValue + 1
                            )
                        }
                    }
                }
            )
            it
        }
        return finalValue
    }

    fun decrease(index: Int, value: Int, type: LinearParams): Int {
        val finalValue = getFinalValue(value)

        _behaviorItemList.update {
            _behaviorItemList.value[index] = _behaviorItemList.value[index].copy(
                linearBehavior = with(_behaviorItemList.value[index].linearBehavior!!) {
                    when (type) {
                        LinearParams.MIN_TEMP -> {
                            copy(
                                minTemp = finalValue - 1
                            )
                        }

                        LinearParams.MAX_TEMP -> {
                            copy(
                                maxTemp = finalValue - 1
                            )
                        }

                        LinearParams.MIN_FAN_SPEED -> {
                            copy(
                                minFanSpeed = finalValue - 1
                            )
                        }

                        LinearParams.MAX_FAN_SPEED -> {
                            copy(
                                maxFanSpeed = finalValue - 1
                            )
                        }
                    }
                }
            )
            it
        }
        return finalValue
    }

    fun onChange(index: Int, value: Int, type: LinearParams): Int {
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
        return finalValue
    }
}