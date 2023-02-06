package model.hardware

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import model.HardwareType

interface BaseH {
    val name: String
    val value: MutableState<Int>
    val type: HardwareType
    val id: String
}

class HControl(
    override val name: String,
    override val id: String,
    value: Int = 0,
) : BaseH {
    override val value: MutableState<Int> = mutableStateOf(value)
    override val type: HardwareType.SensorType = HardwareType.SensorType.H_S_FAN
}

class HFan(
    override val name: String,
    override val id: String,
    value: Int = 0,
) : BaseH {
    override val value: MutableState<Int> = mutableStateOf(value)
    override val type: HardwareType.SensorType = HardwareType.SensorType.H_S_FAN
}


class HTemp(
    override val name: String,
    override val id: String,
    value: Int = 0,
) : BaseH {
    override val value: MutableState<Int> = mutableStateOf(value)
    override val type: HardwareType.SensorType = HardwareType.SensorType.H_S_TEMP
}