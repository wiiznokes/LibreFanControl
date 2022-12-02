package model.hardware.sensor


data class Fan(
    override val index: Int,
    override val id: String,
    override var libName: String = "",
    override var value: Int = 0,
    override var maxValue: Int = 4000,
    override var minValue: Int = 0,
    override var name: String = "",
    override val specifyType: String, override val globalType: String
) : BaseSensor