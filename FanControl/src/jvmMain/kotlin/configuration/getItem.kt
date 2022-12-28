package configuration

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.ItemType
import model.item.ControlItem
import model.item.behavior.BehaviorExtension
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
                name = getJsonValue("name", obj) as String,
                itemId = getJsonValue("itemId", obj) as Long,
                type = when (getJsonValue("type", obj)) {
                    ItemType.ControlType.C_FAN.toString() -> ItemType.ControlType.C_FAN
                    else -> throw Exception("unknown type")
                },
                visible = getJsonValue("visible", obj) as Boolean,
                behaviorId = when (getJsonValue("behaviorId", obj)) {
                    "null" -> null
                    else -> getJsonValue("behaviorId", obj) as Long?
                }
            )

            it
        }


        controlItemList.add(
            ControlItem(
                name = getJsonValue("name", obj),
                itemId = getJsonValue("itemId", obj).toLong(),
                libId = getJsonValue("libId", obj)
            )
        )
    }
}

fun getBehavior(behaviorItemList: SnapshotStateList<BehaviorItem>, array: JSONArray) {
    for (i in 0 until array.length()) {
        val obj = array[i] as JSONObject


        var behaviorExtension: BehaviorExtension? = null

        val behavior = BehaviorItem(
            name = getJsonValue("name", obj),
            itemId = getJsonValue("itemId", obj).toLong(),
            type = when (getJsonValue("type", obj)) {
                ItemType.BehaviorType.B_FLAT.toString() -> {
                    behaviorExtension = getFlatBehavior(obj)
                    ItemType.BehaviorType.B_FLAT
                }
                ItemType.BehaviorType.B_FLAT.toString() -> ItemType.BehaviorType.B_FLAT
                ItemType.BehaviorType.B_FLAT.toString() -> ItemType.BehaviorType.B_FLAT
                else -> throw Exception("type not found")
            }
        )

        when (behavior.type) {
            ItemType.BehaviorType.B_FLAT -> behavior.flatBehavior = behaviorExtension as FlatBehavior
            ItemType.BehaviorType.B_LINEAR -> behavior.linearBehavior = behaviorExtension as LinearBehavior
            ItemType.BehaviorType.B_TARGET -> TODO()
            ItemType.BehaviorType.B_UNSPECIFIED -> throw Exception("unspecified type")
        }

        behaviorItemList.add(behavior)
    }
}


private fun getFlatBehavior(obj: JSONObject): FlatBehavior {
    return FlatBehavior(
        value = getJsonValue("value", obj).toInt()
    )
}

private fun getLinearBehavior(obj: JSONObject): LinearBehavior {
    return LinearBehavior(
        minTemp = getJsonValue("minTemp", obj).toInt(),
        maxTemp = getJsonValue("maxTemp", obj).toInt(),
        minFanSpeed = getJsonValue("minFanSpeed", obj).toInt(),
        maxFanSpeed = getJsonValue("maxFanSpeed", obj).toInt(),
    )
}