package model.item.sensor

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

enum class CustomTempType {
    average,
    max,
    min;

    companion object {
        infix fun from(value: String): CustomTempType = CustomTempType.values().first { it.name == value }
    }
}

data class CustomTemp(
    val type: CustomTempType = CustomTempType.average,
    val sensorIdList: SnapshotStateList<Long> = mutableStateListOf(),
    var value: Int = 0
) : SensorExtension