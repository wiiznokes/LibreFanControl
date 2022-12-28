package model.item.behavior

import ui.utils.Resources

data class LinearBehavior(
    val minTemp: Int = 0,
    val maxTemp: Int = 100,
    val minFanSpeed: Int = 50,
    val maxFanSpeed: Int = 100,
    var tempName: String = Resources.getString("none"),
    var sensorId: String? = null,
): BehaviorExtension