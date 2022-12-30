package configuration.read

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.Sensor
import org.json.JSONArray
import org.json.JSONObject
import utils.getJsonValue

class ReadHardware {
    fun getSensors(
        sensorList: MutableStateFlow<SnapshotStateList<Sensor>>,
        array: JSONArray
    ) {
        for (i in 0 until array.length()) {
            val obj = array[i] as JSONObject

            val index = sensorList.value.indexOfFirst {
                it.libId == getJsonValue("libId", obj)!!
            }
            sensorList.update {
                it[index] = it[index].copy(
                    id = getJsonValue("id", obj)!!
                )
                it
            }
        }
    }
}