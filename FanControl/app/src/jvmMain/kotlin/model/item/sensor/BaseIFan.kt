package model.item.sensor

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import model.ItemType
import model.item.BaseI


class IFan(
    override val name: MutableState<String>,
    override val id: String
): BaseI{
    override val type: ItemType.SensorType = ItemType.SensorType.I_S_FAN


    val hFanId: MutableState<String?> = mutableStateOf(null)
}