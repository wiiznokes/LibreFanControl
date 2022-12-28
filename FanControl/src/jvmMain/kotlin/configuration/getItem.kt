package configuration

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.ItemType
import model.UnspecifiedTypeException
import model.getType
import model.hardware.Sensor
import model.item.ControlItem
import model.item.SensorItem
import model.item.behavior.BehaviorItem
import model.item.behavior.FlatBehavior
import model.item.behavior.LinearBehavior
import org.json.JSONArray
import org.json.JSONObject
import utils.getJsonValue

fun getControls(controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>>, array: JSONArray) {
    for (i in 0 until array.length()) {
        val obj = array[i] as JSONObject

        val index = controlItemList.value.indexOfFirst {
            it.libId == getJsonValue("libId", obj)
        }

        controlItemList.update {
            it[index] = it[index].copy(
                name = getJsonValue("name", obj)!!,
                itemId = getJsonValue("itemId", obj)!!,
                type = getType(getJsonValue("type", obj)!!) as ItemType.ControlType,
                visible = getJsonValue("visible", obj)!!,
                behaviorId = getJsonValue("behaviorId", obj),
                isActive = getJsonValue("isActive", obj)!!,
            )
            it
        }
    }
}

fun getBehaviors(behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>>, array: JSONArray) {
    for (i in 0 until array.length()) {
        val obj = array[i] as JSONObject

        val type = getType(getJsonValue("type", obj)!!) as ItemType.BehaviorType

        behaviorItemList.update {
            it.add(
                BehaviorItem(
                    name = getJsonValue("name", obj)!!,
                    itemId = getJsonValue("itemId", obj)!!,
                    type = type,
                    extension = when (type) {
                        ItemType.BehaviorType.B_FLAT -> getFlatBehavior(obj)
                        ItemType.BehaviorType.B_LINEAR -> getLinearBehavior(obj)
                        ItemType.BehaviorType.B_TARGET -> TODO()
                        ItemType.BehaviorType.B_UNSPECIFIED -> throw UnspecifiedTypeException()
                    }
                )
            )
            it
        }
    }
}


fun getSensors(
    sensorItemList: MutableStateFlow<SnapshotStateList<SensorItem>>,
    sensorList: List<Sensor>,
    array: JSONArray
) {

    for (i in 0 until array.length()) {
        val obj = array[i] as JSONObject

        val libId = getJsonValue<String>("libId", obj)

        val sensor = when (libId) {
            null -> null
            else -> sensorList.find {
                it.libId == libId
            }
        }

        val sensorItem = SensorItem(
            name = getJsonValue("name", obj)!!,
            itemId = getJsonValue("itemId", obj)!!,
            type = getType(getJsonValue("type", obj)!!) as ItemType.SensorType,
            libId = libId,
        )

        sensorItemList.update {
            it.add(
                if (sensor != null) {
                    sensorItem.copy(
                        sensorName = sensor.libName
                    )
                } else
                    sensorItem
            )
            it
        }
    }
}


private fun getFlatBehavior(obj: JSONObject): FlatBehavior {
    return FlatBehavior(
        value = getJsonValue("value", obj)!!
    )
}

private fun getLinearBehavior(obj: JSONObject): LinearBehavior {
    return LinearBehavior(
        minTemp = getJsonValue("minTemp", obj)!!,
        maxTemp = getJsonValue("maxTemp", obj)!!,
        minFanSpeed = getJsonValue("minFanSpeed", obj)!!,
        maxFanSpeed = getJsonValue("maxFanSpeed", obj)!!,
    )
}