package model.hardware

import model.HardwareType

interface BaseHardware {
    val libIndex: Int
    val libId: String
    val libName: String
    var value: Int
    val type: HardwareType
    var id: Long
}