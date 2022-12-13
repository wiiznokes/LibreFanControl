package model.hardware

import model.ItemType


data class Sensor(
    override val libIndex: Int,
    override val libId: String,
    override val libName: String,
    override var value: Int = 0,
    val type: ItemType.SensorType
) : BaseHardware