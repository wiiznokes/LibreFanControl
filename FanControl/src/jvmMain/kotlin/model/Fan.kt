package model

data class Fan(
    val index: Int,
    val id: String,
    var name: String = "",
    var libName: String = "",
    var value: Int = 0
)
