package configuration

import model.ItemType
import model.item.BaseItem
import model.item.ControlItem
import model.item.SensorItem
import model.item.behavior.BehaviorItem
import model.item.behavior.FlatBehavior
import model.item.behavior.LinearBehavior
import org.json.JSONWriter


fun setItems(itemList: List<BaseItem>, writer: JSONWriter, type: ItemType) {
    writer.key("$type")
    writer.array()
    itemList.forEach {
        writer.`object`()
        when(type) {
            is ItemType.ControlType -> setControl(it as ControlItem, writer)
            is ItemType.BehaviorType -> setBehavior(it as BehaviorItem, writer)
            is ItemType.SensorType -> setSensor(it as SensorItem, writer)
        }
        writer.endObject()
    }
    writer.endArray()
}

fun setControl(controlItem: ControlItem, writer: JSONWriter) {
    writer.key("name")
    writer.value(controlItem.name)
    writer.key("itemId")
    writer.value(controlItem.itemId)
    writer.key("type")
    writer.value(controlItem.type)
    writer.key("visible")
    writer.value(controlItem.visible)
    writer.key("behaviorId")
    writer.value(controlItem.behaviorId)
    writer.key("isActive")
    writer.value(controlItem.isActive)
    writer.key("libId")
    writer.value(controlItem.libId)
}

fun setSensor(sensorItem: SensorItem, writer: JSONWriter) {
    writer.key("name")
    writer.value(sensorItem.name)
    writer.key("itemId")
    writer.value(sensorItem.itemId)
    writer.key("type")
    writer.value(sensorItem.type)
    writer.key("sensorId")
    writer.value(sensorItem.sensorId)
}

fun setBehavior(behaviorItem: BehaviorItem, writer: JSONWriter) {
    writer.key("name")
    writer.value(behaviorItem.name)
    writer.key("itemId")
    writer.value(behaviorItem.itemId)
    writer.key("type")
    writer.value(behaviorItem.type)

    when(behaviorItem.type) {
        ItemType.BehaviorType.FLAT -> setFlatBehavior(behaviorItem.flatBehavior!!, writer)
        ItemType.BehaviorType.LINEAR -> setLinearBehavior(behaviorItem.linearBehavior!!, writer)
        ItemType.BehaviorType.TARGET -> TODO()
        else -> throw Exception("unspecified item type")
    }
}

private fun setFlatBehavior(flatBehavior: FlatBehavior, writer: JSONWriter) {
    writer.key("value")
    writer.value(flatBehavior.value)
}

private fun setLinearBehavior(linearBehavior: LinearBehavior, writer: JSONWriter) {
    writer.key("minTemp")
    writer.value(linearBehavior.minTemp)
    writer.key("maxTemp")
    writer.value(linearBehavior.maxTemp)
    writer.key("minFanSpeed")
    writer.value(linearBehavior.minFanSpeed)
    writer.key("maxFanSpeed")
    writer.value(linearBehavior.maxFanSpeed)
    writer.key("sensorId")
    writer.value(linearBehavior.sensorId)
}