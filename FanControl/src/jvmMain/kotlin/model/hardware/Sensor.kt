package model.hardware

import model.SensorType

data class Sensor(
    override val libIndex: Int,
    override val libId: String,
    override val libName: String,
    override var value: Int = 0,
    val type: SensorType
) : BaseHardware