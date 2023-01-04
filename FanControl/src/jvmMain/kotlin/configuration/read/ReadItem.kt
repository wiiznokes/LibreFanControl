package configuration.read

import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.UnspecifiedTypeException
import model.getItemType
import model.item.Control
import model.item.SensorItem
import model.item.behavior.Behavior
import model.item.behavior.Flat
import model.item.behavior.Linear
import model.item.behavior.Target
import org.json.JSONArray
import org.json.JSONObject
import utils.getJsonValue

class ReadItem {
    fun getControls(controlList: SnapshotStateList<Control>, array: JSONArray) {

        for (i in 0 until array.length()) {
            val obj = array[i] as JSONObject

            val index = controlList.indexOfFirst { control ->
                control.libId == getJsonValue("libId", obj)
            }

            val isAuto: Boolean = getJsonValue("isAuto", obj)!!

            controlList[index] = controlList[index].copy(
                name = getJsonValue("name", obj)!!,
                id = getJsonValue("id", obj)!!,
                type = getItemType(getJsonValue("type", obj)!!) as ItemType.ControlType,
                visible = getJsonValue("visible", obj)!!,
                behaviorId = getJsonValue("behaviorId", obj),
                isAuto = isAuto
            )
        }
    }

    fun getBehaviors(
        behaviorList: SnapshotStateList<Behavior>,
        array: JSONArray
    ) {
        for (i in 0 until array.length()) {
            val obj = array[i] as JSONObject

            val type = getItemType(getJsonValue("type", obj)!!) as ItemType.BehaviorType


            behaviorList.add(
                Behavior(
                    name = getJsonValue("name", obj)!!,
                    id = getJsonValue("id", obj)!!,
                    type = type,
                    extension = when (type) {
                        ItemType.BehaviorType.I_B_FLAT -> getFlatBehavior(obj)
                        ItemType.BehaviorType.I_B_LINEAR -> getLinearBehavior(obj)
                        ItemType.BehaviorType.I_B_TARGET -> getTargetBehavior(obj)
                        ItemType.BehaviorType.I_B_UNSPECIFIED -> throw UnspecifiedTypeException()
                    }
                )
            )

        }
    }

    fun getSensors(
        sensorItemList: SnapshotStateList<SensorItem>,
        array: JSONArray
    ) {
        for (i in 0 until array.length()) {
            val obj = array[i] as JSONObject

            sensorItemList.add(
                SensorItem(
                    name = getJsonValue("name", obj)!!,
                    id = getJsonValue("id", obj)!!,
                    type = getItemType(getJsonValue("type", obj)!!) as ItemType.SensorType,
                    sensorId = getJsonValue("sensorId", obj),
                )
            )
        }
    }


    private fun getFlatBehavior(obj: JSONObject): Flat {
        return Flat(
            value = getJsonValue("value", obj)!!
        )
    }

    private fun getLinearBehavior(obj: JSONObject): Linear {
        return Linear(
            minTemp = getJsonValue("minTemp", obj)!!,
            maxTemp = getJsonValue("maxTemp", obj)!!,
            minFanSpeed = getJsonValue("minFanSpeed", obj)!!,
            maxFanSpeed = getJsonValue("maxFanSpeed", obj)!!,
            tempSensorId = getJsonValue("tempSensorId", obj)
        )
    }

    private fun getTargetBehavior(obj: JSONObject): Target {
        return Target(
            idleTemp = getJsonValue("idleTemp", obj)!!,
            loadTemp = getJsonValue("loadTemp", obj)!!,
            idleFanSpeed = getJsonValue("idleFanSpeed", obj)!!,
            loadFanSpeed = getJsonValue("loadFanSpeed", obj)!!,
            tempSensorId = getJsonValue("tempSensorId", obj)
        )
    }
}