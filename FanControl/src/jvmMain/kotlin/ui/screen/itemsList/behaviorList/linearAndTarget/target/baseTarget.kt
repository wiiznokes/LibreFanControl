package ui.screen.itemsList.behaviorList.linearAndTarget.target

import model.item.behavior.Target
import ui.utils.Resources

val targetValues = listOf(
    "40", "60", "50", "100"
)

fun targetValues(target: Target) = listOf(
    target.idleTemp,
    target.loadTemp,
    target.idleFanSpeed,
    target.loadFanSpeed
)

val targetPrefixes = listOf(
    Resources.getString("target/idle_temp"),
    Resources.getString("target/load_temp"),
    Resources.getString("target/idle_fan_speed"),
    Resources.getString("target/load_fan_speed")
)

val targetTypes = listOf(
    TargetParams.IDLE_TEMP,
    TargetParams.LOAD_TEMP,
    TargetParams.IDLE_FAN_SPEED,
    TargetParams.LOAD_FAN_SPEED
)