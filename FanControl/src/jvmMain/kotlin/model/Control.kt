package model

data class Control(
    val index: Int,
    val id: String,
    var name: String = "",
    var libName: String = "",
    var isAuto: Boolean = true,
    var value: Int = 0
)