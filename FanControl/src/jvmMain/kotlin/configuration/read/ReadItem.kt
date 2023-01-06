package configuration.read

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import configuration.LoadConfigException
import model.ItemType
import model.UnspecifiedTypeException
import model.item.behavior.Behavior
import model.item.behavior.Flat
import model.item.behavior.Linear
import model.item.behavior.Target
import model.item.control.Control
import model.item.sensor.*
import org.json.JSONArray
import org.json.JSONObject
import utils.getJsonValue

class ReadItem {
    fun getControls(controlList: SnapshotStateList<Control>, array: JSONArray) {
        for (i in 0 until array.length()) {
            val obj = array[i] as JSONObject

            val index = controlList.indexOfFirst { control ->
                control.libId == getJsonValue("libId", obj)!!
            }
            if (index == -1)
                throw LoadConfigException()

            val isAuto: Boolean = getJsonValue("isAuto", obj)!!

            controlList[index] = controlList[index].copy(
                name = getJsonValue("name", obj)!!,
                id = getJsonValue("id", obj)!!,
                type = ItemType.ControlType from getJsonValue("type", obj)!!,
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

            val type = ItemType.BehaviorType from getJsonValue("type", obj)!!


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

            val type = ItemType.SensorType from getJsonValue("type", obj)!!

            sensorItemList.add(
                SensorItem(
                    name = getJsonValue("name", obj)!!,
                    id = getJsonValue("id", obj)!!,
                    type = type,
                    extension = when (type) {
                        ItemType.SensorType.I_S_FAN -> getFan(obj)
                        ItemType.SensorType.I_S_TEMP -> getTemp(obj)
                        ItemType.SensorType.I_S_CUSTOM_TEMP -> getCustomTemp(obj)
                        ItemType.SensorType.I_S_UNSPECIFIED -> throw UnspecifiedTypeException()
                    }

                )
            )
        }
    }

    private fun getFan(obj: JSONObject): Fan {
        return Fan(
            sensorId = getJsonValue("sensorId", obj)
        )
    }

    private fun getTemp(obj: JSONObject): Temp {
        return Temp(
            sensorId = getJsonValue("sensorId", obj)
        )
    }

    private fun getCustomTemp(obj: JSONObject): CustomTemp {
        val sensorIdList = mutableStateListOf<Long>()

        obj.getJSONArray("sensorIdList").let {
            for (i in 0 until it.length()) {
                sensorIdList.add(it[i] as Long)
            }
        }

        return CustomTemp(
            type = CustomTempType from getJsonValue("type", obj)!!,
            sensorIdList = sensorIdList
        )
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