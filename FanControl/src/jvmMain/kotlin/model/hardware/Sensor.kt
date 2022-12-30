package model.hardware

import model.HardwareType


data class Sensor(
    override val libIndex: Int,
    override val libId: String,
    override val libName: String,
    override var value: Int = 0,
    override val type: HardwareType.SensorType,
    // id is var because it can be overridden if there is a configuration
    override var id: Long,
) : BaseHardware