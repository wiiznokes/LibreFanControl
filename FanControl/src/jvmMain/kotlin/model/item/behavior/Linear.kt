package model.item.behavior

data class Linear(
    var minTemp: Int = 25,
    var maxTemp: Int = 60,
    var minFanSpeed: Int = 10,
    var maxFanSpeed: Int = 100,
    var tempSensorId: Long? = null,

    override var value: Int = 0
) : BehaviorExtension {
    override fun copyI(value: Int): BehaviorExtension = copy(
        value = value
    )
}