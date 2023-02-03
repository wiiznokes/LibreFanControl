package ui.screen.itemsList.behaviorList.linearAndTarget.linear

import model.item.behavior.Linear
import ui.utils.Resources



fun linearValues(linear: Linear) = listOf(
    linear.minTemp,
    linear.maxTemp,
    linear.minFanSpeed,
    linear.maxFanSpeed
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

