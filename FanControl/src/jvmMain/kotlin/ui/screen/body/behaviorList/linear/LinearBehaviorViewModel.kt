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

class LinearBehaviorViewModel(
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList
) {

    fun increase(index: Int, value: Int, type: LinearParams) {
        if (value >= 100) return
        _behaviorItemList.update {
            _behaviorItemList.value[index] = _behaviorItemList.value[index].copy(
                linearBehavior = with(_behaviorItemList.value[index].linearBehavior!!) {
                    when (type) {
                        LinearParams.MIN_TEMP -> {
                            copy(
                                minTemp = value + 1
                            )
                        }

                        LinearParams.MAX_TEMP -> {
                            copy(
                                maxTemp = value + 1
                            )
                        }

                        LinearParams.MIN_FAN_SPEED -> {
                            copy(
                                minFanSpeed = value + 1
                            )
                        }

                        LinearParams.MAX_FAN_SPEED -> {
                            copy(
                                maxFanSpeed = value + 1
                            )
                        }
                    }
                }
            )
            it
        }
    }

    fun decrease(index: Int, value: Int, type: LinearParams) {
        if (value <= 0) return
        _behaviorItemList.update {
            _behaviorItemList.value[index] = _behaviorItemList.value[index].copy(
                linearBehavior = with(_behaviorItemList.value[index].linearBehavior!!) {
                    when (type) {
                        LinearParams.MIN_TEMP -> {
                            copy(
                                minTemp = value - 1
                            )
                        }

                        LinearParams.MAX_TEMP -> {
                            copy(
                                maxTemp = value - 1
                            )
                        }

                        LinearParams.MIN_FAN_SPEED -> {
                            copy(
                                minFanSpeed = value - 1
                            )
                        }

                        LinearParams.MAX_FAN_SPEED -> {
                            copy(
                                maxFanSpeed = value - 1
                            )
                        }
                    }
                }
            )
            it
        }
    }

    fun onChange(index: Int, text: String, type: LinearParams) {
        val value = text.toInt()
        if (value <= 0) return
        _behaviorItemList.update {
            _behaviorItemList.value[index] = _behaviorItemList.value[index].copy(
                linearBehavior = with(_behaviorItemList.value[index].linearBehavior!!) {
                    when (type) {
                        LinearParams.MIN_TEMP -> {
                            copy(
                                minTemp = value
                            )
                        }

                        LinearParams.MAX_TEMP -> {
                            copy(
                                maxTemp = value
                            )
                        }

                        LinearParams.MIN_FAN_SPEED -> {
                            copy(
                                minFanSpeed = value
                            )
                        }

                        LinearParams.MAX_FAN_SPEED -> {
                            copy(
                                maxFanSpeed = value
                            )
                        }
                    }
                }
            )
            it
        }
    }
}