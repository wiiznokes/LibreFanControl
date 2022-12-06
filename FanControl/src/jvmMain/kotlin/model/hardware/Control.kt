package model.hardware

data class Control(
    override val libIndex: Int,
    override val libId: String,
    override val libName: String,
    override var value: Int = 0,

    var maxValue: Int = 4000,
    var minValue: Int = 0,
    var isAuto: Boolean = true,

    var isVisible: Boolean = true
) : BaseHardware