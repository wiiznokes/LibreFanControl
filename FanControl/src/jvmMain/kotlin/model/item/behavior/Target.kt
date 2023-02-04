package model.item.behavior

data class Target(
    var idleTemp: Int = 40,
    var loadTemp: Int = 60,
    var idleFanSpeed: Int = 50,
    var loadFanSpeed: Int = 100,

    var hTempId: Long? = null,

    override var value: Int = 0
) : BehaviorExtension {
    override fun copyI(value: Int): BehaviorExtension = copy(
        value = value
    )
}