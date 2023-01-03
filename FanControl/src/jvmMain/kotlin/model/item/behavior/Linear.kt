package model.item.behavior

data class Linear(
    var minTemp: Int = 0,
    var maxTemp: Int = 100,
    var minFanSpeed: Int = 50,
    var maxFanSpeed: Int = 100,
    var tempSensorId: Long? = null,

    var value: Int = 0
) : BehaviorExtension