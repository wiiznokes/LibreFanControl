package model.item.sensor

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import model.ItemType
import model.item.BaseI


class IFan(
    name: String,
    override val id: String,
    hFanId: String? = null,
) : BaseI {
    override val name: MutableState<String> = mutableStateOf(name)
    override val type: ItemType.SensorType = ItemType.SensorType.I_S_FAN
    val hFanId: MutableState<String?> = mutableStateOf(hFanId)
}