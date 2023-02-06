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
    override val name: MutableState<String>,
    override val id: String
): BaseITemp{
    override val type: ItemType.SensorType = ItemType.SensorType.I_S_TEMP


    val hTempId: MutableState<String?> = mutableStateOf(null)
}















enum class CustomTempType {
    AVERAGE,
    MAX,
    MIN
}

class ICustomTemp(
    override val name: MutableState<String>,
    override val id: String
): BaseITemp{
    override val type: ItemType.SensorType = ItemType.SensorType.I_S_CUSTOM_TEMP

    val customTempType: MutableState<CustomTempType> = mutableStateOf(CustomTempType.AVERAGE)
    val sensorIdList: SnapshotStateList<String> = mutableStateListOf()

    val value: MutableState<Int> = mutableStateOf(0)
}