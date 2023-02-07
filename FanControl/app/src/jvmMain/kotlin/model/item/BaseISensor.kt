package model.item

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import model.ItemType


interface BaseISensor : BaseI {
    override val type: ItemType.SensorType
}


interface BaseITemp : BaseISensor


class IFan(
    name: String,
    override val id: String,
    hFanId: String? = null,
) : BaseISensor {
    override val name: MutableState<String> = mutableStateOf(name)
    override val type: ItemType.SensorType = ItemType.SensorType.I_S_FAN
    val hFanId: MutableState<String?> = mutableStateOf(hFanId)
}


class ITemp(
    name: String,
    override val id: String,
    hTempId: String? = null,
) : BaseITemp {
    override val name: MutableState<String> = mutableStateOf(name)

    override val type: ItemType.SensorType = ItemType.SensorType.I_S_TEMP

    val hTempId: MutableState<String?> = mutableStateOf(hTempId)

}


enum class CustomTempType {
    average,
    max,
    min
}

class ICustomTemp(
    name: String,
    override val id: String,

    customTempType: CustomTempType = CustomTempType.average,
     hTempIds: List<String> = listOf(),

    value: Int = 0,
) : BaseITemp {
    override val name: MutableState<String> = mutableStateOf(name)
    override val type: ItemType.SensorType = ItemType.SensorType.I_S_CUSTOM_TEMP

    val hTempIds: List<String> = hTempIds.toMutableStateList()
    val customTempType: MutableState<CustomTempType> = mutableStateOf(customTempType)

    val value: MutableState<Int> = mutableStateOf(value)
}