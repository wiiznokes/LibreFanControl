package ui.screen.itemsList.behaviorList.linearAndTarget.linear

import model.item.ILinear
import utils.Resources


fun linearValues(linear: ILinear) = listOf(
    linear.minTemp.value,
    linear.maxTemp.value,
    linear.minFanSpeed.value,
    linear.maxFanSpeed.value
)

val linearPrefixes = listOf(
    Resources.getString("linear/min_temp"),
    Resources.getString("linear/max_temp"),
    Resources.getString("linear/min_fan_speed"),
    Resources.getString("linear/max_fan_speed")
)

val linearTypes = listOf(
    LinearParams.MIN_TEMP,
    LinearParams.MAX_TEMP,
    LinearParams.MIN_FAN_SPEED,
    LinearParams.MAX_FAN_SPEED
)

