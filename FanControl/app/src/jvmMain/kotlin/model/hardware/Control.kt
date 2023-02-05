package model.hardware

import model.HardwareType

data class Control(
    override val name: String,
    override var value: Int = 0,
    override val type: HardwareType.ControlType = HardwareType.ControlType.H_C_FAN,
    override var id: Long
) : BaseHardware
