package configuration

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.ItemType
import model.UnspecifiedTypeException
import model.getType
import model.item.ControlItem
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

fun getBehavior(behaviorItemList: SnapshotStateList<BehaviorItem>, array: JSONArray) {
    for (i in 0 until array.length()) {
        val obj = array[i] as JSONObject

        val type = getType(getJsonValue("type", obj)!!) as ItemType.BehaviorType

        behaviorItemList.add(
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