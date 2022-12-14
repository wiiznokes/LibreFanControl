package configuration.read

import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Sensor
import org.json.JSONArray
import org.json.JSONObject
import utils.getJsonValue

class ReadHardware {
    fun getSensors(
        sensorList: SnapshotStateList<Sensor>,
        array: JSONArray
    ) {
        for (i in 0 until array.length()) {
            val obj = array[i] as JSONObject

            val index = sensorList.indexOfFirst {
                it.libId == getJsonValue("libId", obj)!!
            }
            sensorList[index] = sensorList[index].copy(
                id = getJsonValue("id", obj)!!
            )
        }
    }
}