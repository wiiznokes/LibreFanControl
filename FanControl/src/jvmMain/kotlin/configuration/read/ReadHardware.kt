package configuration.read

import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Control
import model.hardware.Sensor
import org.json.JSONArray
import org.json.JSONObject
import utils.getJsonValue

class ReadHardware {
    fun getSensors(
        hSensors: SnapshotStateList<Sensor>,
        array: JSONArray
    ) {
        for (i in 0 until array.length()) {
            val obj = array[i] as JSONObject

            val index = hSensors.indexOfFirst {
                it.id == getJsonValue("id", obj)!!
            }
            hSensors[index] = hSensors[index].copy(
                id = getJsonValue("id", obj)!!
            )
        }
    }


    fun getControls(
        hControls: SnapshotStateList<Control>,
        array: JSONArray
    ) {
        for (i in 0 until array.length()) {
            val obj = array[i] as JSONObject

            val index = hControls.indexOfFirst {
                it.id == getJsonValue("id", obj)!!
            }
            hControls[index] = hControls[index].copy(
                id = getJsonValue("id", obj)!!
            )
        }
    }


}