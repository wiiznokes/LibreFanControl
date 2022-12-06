package model.hardware

interface BaseHardware {
    val libIndex: Int
    val libId: String
    val libName: String
    var value: Int
}