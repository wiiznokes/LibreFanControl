package model.item.sensor

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.item.BaseI


interface BaseITemp : BaseI {
    override val name: MutableState<String>
    override val type: ItemType.SensorType
    override val id: String
}



class ITemp(
    name: String,
    override val id: String,
    hTempId: String? = null
): BaseITemp{
    override val name: MutableState<String> = mutableStateOf(name)

    override val type: ItemType.SensorType = ItemType.SensorType.I_S_TEMP

    val hTempId: MutableState<String?> = mutableStateOf(hTempId)

}















enum class CustomTempType {
    AVERAGE,
    MAX,
    MIN
}

class ICustomTemp(
    name: String,
    override val id: String,

    customTempType: CustomTempType = CustomTempType.AVERAGE,
    val sensorIdList: SnapshotStateList<String> = mutableStateListOf(),

    value: Int = 0
): BaseITemp{
    override val name: MutableState<String> = mutableStateOf(name)
    override val type: ItemType.SensorType = ItemType.SensorType.I_S_CUSTOM_TEMP

    val customTempType: MutableState<CustomTempType> = mutableStateOf(customTempType)

    val value: MutableState<Int> = mutableStateOf(value)
}