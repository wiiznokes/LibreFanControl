package model.hardware

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

interface BaseH {
    val name: String
    val value: MutableState<Int>
    val id: String
}

class HControl(
    override val name: String,
    override val id: String,
    value: Int = 0,
) : BaseH {
    override val value: MutableState<Int> = mutableStateOf(value)
}

class HFan(
    override val name: String,
    override val id: String,
    value: Int = 0,
) : BaseH {
    override val value: MutableState<Int> = mutableStateOf(value)
}


class HTemp(
    override val name: String,
    override val id: String,
    value: Int = 0,
) : BaseH {
    override val value: MutableState<Int> = mutableStateOf(value)
}