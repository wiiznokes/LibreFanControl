package configuration.read

import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.UnspecifiedTypeException
import model.getItemType
import model.item.ControlItem
import model.item.SensorItem
import model.item.behavior.BehaviorItem
import model.item.behavior.FlatBehavior
import model.item.behavior.LinearBehavior
import org.json.JSONArray
import org.json.JSONObject
import utils.getJsonValue

class ReadItem {
    fun getControls(controlItemList: SnapshotStateList<ControlItem>, array: JSONArray) {

        for (i in 0 until array.length()) {
            val obj = array[i] as JSONObject

            val index = controlItemList.indexOfFirst { control ->
                control.libId == getJsonValue("libId", obj)
            }

            val isAuto: Boolean = getJsonValue("isAuto", obj)!!

            controlItemList[index] = controlItemList[index].copy(
                name = getJsonValue("name", obj)!!,
                itemId = getJsonValue("itemId", obj)!!,
                type = getItemType(getJsonValue("type", obj)!!) as ItemType.ControlType,
                visible = getJsonValue("visible", obj)!!,
                behaviorId = getJsonValue("behaviorId", obj),
                isAuto = isAuto
            )
        }
    }

    fun getBehaviors(
        behaviorItemList: SnapshotStateList<BehaviorItem>,
        array: JSONArray
    ) {
        for (i in 0 until array.length()) {
            val obj = array[i] as JSONObject

            val type = getItemType(getJsonValue("type", obj)!!) as ItemType.BehaviorType


            behaviorItemList.add(
                BehaviorItem(
                    name = getJsonValue("name", obj)!!,
                    itemId = getJsonValue("itemId", obj)!!,
                    type = type,
                    extension = when (type) {
                        ItemType.BehaviorType.I_B_FLAT -> getFlatBehavior(obj)
                        ItemType.BehaviorType.I_B_LINEAR -> getLinearBehavior(obj)
                        ItemType.BehaviorType.I_B_TARGET -> TODO()
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
                    itemId = getJsonValue("itemId", obj)!!,
                    type = getItemType(getJsonValue("type", obj)!!) as ItemType.SensorType,
                    sensorId = getJsonValue("sensorId", obj),
                )
            )
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
            tempSensorId = getJsonValue("tempSensorId", obj)
        )

    }
}