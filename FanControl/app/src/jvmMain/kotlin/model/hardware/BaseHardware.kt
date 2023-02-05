package model.hardware

import model.HardwareType

interface BaseHardware {
    val name: String
    var value: Int
    val type: HardwareType
    var id: Long
}