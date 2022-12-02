package model.hardware.sensor


data class Temp(
    override val index: Int,
    override val id: String,
    override var name: String = "",
    override var libName: String = "",
    override var value: Int = 0,
    override var maxValue: Int = 100,
    override var minValue: Int = 0,
    override val specifyType: String, override val globalType: String
) : BaseSensor