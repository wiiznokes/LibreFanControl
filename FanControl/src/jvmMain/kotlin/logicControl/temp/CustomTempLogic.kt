package logicControl.temp

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Sensor
import model.item.sensor.CustomTemp
import model.item.sensor.CustomTempType

class CustomTempLogic(
    private val tempList: SnapshotStateList<Sensor> = State.sensorLists.tempList
) {

    fun getValue(
        customTemp: CustomTemp
    ): Int? {

        if (customTemp.sensorIdList.isEmpty())
            return null

        val values = getTempListValue(customTemp.sensorIdList)

        return when (customTemp.type) {
            CustomTempType.average -> values.average().toInt()
            CustomTempType.max -> values.max()
            CustomTempType.min -> values.min()
        }
    }


    private fun getTempListValue(idList: List<Long>): List<Int> {
        val valueList = mutableListOf<Int>()
        idList.forEach { id ->
            valueList.add(
                tempList.first {
                    it.id == id
                }.value
            )
        }
        return valueList
    }
}
