package model.hardware

import model.HardwareType


data class Sensor(
    override val name: String,
    override var value: Int = 0,
    override val type: HardwareType.SensorType,
    override var id: Long
) : BaseHardware