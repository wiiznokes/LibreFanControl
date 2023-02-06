package ui.screen.itemsList.behaviorList.linearAndTarget.target

import model.item.behavior.ITarget
import ui.utils.Resources


fun targetValues(target: ITarget) = listOf(
    target.idleTemp.value,
    target.loadTemp.value,
    target.idleFanSpeed.value,
    target.loadFanSpeed.value
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