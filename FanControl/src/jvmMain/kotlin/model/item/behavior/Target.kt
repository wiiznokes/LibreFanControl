package model.item.behavior

data class Target(
    var idleTemp: Int,
    var loadTemp: Int,
    var idleFanSpeed: Int,
    var loadFanSpeed: Int,

    var tempSensorId: Long? = null,

    override var value: Int = 0,
    var idleHasBeenReach: Boolean = false
) : BehaviorExtension {
    override fun copyI(value: Int): BehaviorExtension = copy(
        value = value
    )
}