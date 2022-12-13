package model.item.behavior

data class LinearBehavior(
    val minTemp: Int = 0,
    val maxTemp: Int = 100,
    val minFanSpeed: Int = 50,
    val maxFanSpeed: Int = 100
)