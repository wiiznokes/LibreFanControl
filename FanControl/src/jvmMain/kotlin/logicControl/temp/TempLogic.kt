package logicControl.temp

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Sensor
import model.item.sensor.CustomTemp
import model.item.sensor.CustomTempType
import model.item.sensor.SensorItem

class TempLogic {

    companion object {
        private val tempList: SnapshotStateList<Sensor> = State.sensorLists.tempList
        private val tempItemList: SnapshotStateList<SensorItem> = State.tempItemList


        fun getValue(id: Long?): Int? {
            return when {
                id == null -> null
                id > 0 -> getTempValue(id)
                id < 0 -> getCustomTempValue(id)
                else -> throw IllegalArgumentException()
            }
        }


        private fun getCustomTempValue(
            id: Long
        ): Int? {
            val customTemp = tempItemList.first { it.id == id }.extension as CustomTemp
            if (customTemp.sensorIdList.isEmpty())
                return null

            val values = getTempListValues(customTemp.sensorIdList)

            return when (customTemp.customTempType) {
                CustomTempType.average -> values.average().toInt()
                CustomTempType.max -> values.max()
                CustomTempType.min -> values.min()
            }
        }


        private fun getTempValue(id: Long): Int {
            return tempList.first {
                it.id == id
            }.value
        }


        private fun getTempListValues(idList: List<Long>): List<Int> {
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
}
