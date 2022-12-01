package model

data class Temp(
    val index: Int,
    val id: String,
    var name: String = "",
    var libName: String = "",
    var value: Int = 0,
    var maxValue: Int = 100,
    var minValue: Int = 0
)