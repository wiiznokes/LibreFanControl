package configuration.write

import model.ItemType
import model.UnspecifiedTypeException
import model.item.BaseItem
import model.item.Control
import model.item.SensorItem
import model.item.behavior.Behavior
import model.item.behavior.Flat
import model.item.behavior.Linear
import model.item.behavior.Target
import org.json.JSONWriter


class WriteItem {

    fun setItems(itemList: List<BaseItem>, writer: JSONWriter, type: ItemType) {
        writer.key("$type")
        writer.array()
        itemList.forEach {
            writer.`object`()
            when (type) {
                is ItemType.ControlType -> setControl(it as Control, writer)
                is ItemType.BehaviorType -> setBehavior(it as Behavior, writer)
                is ItemType.SensorType -> setSensorItem(it as SensorItem, writer)
            }
            writer.endObject()
        }
        writer.endArray()
    }

    private fun setControl(control: Control, writer: JSONWriter) {
        setItem(control, writer)

        writer.key("visible")
        writer.value(control.visible)
        writer.key("behaviorId")
        writer.value(control.behaviorId)
        writer.key("isAuto")
        writer.value(control.isAuto)
        writer.key("libId")
        writer.value(control.libId)
    }

    private fun setBehavior(behavior: Behavior, writer: JSONWriter) {
        setItem(behavior, writer)

        when (behavior.type) {
            ItemType.BehaviorType.I_B_FLAT -> setFlatBehavior(
                flat = behavior.extension as Flat,
                writer = writer
            )

            ItemType.BehaviorType.I_B_LINEAR -> setLinearBehavior(
                linear = behavior.extension as Linear,
                writer = writer
            )

            ItemType.BehaviorType.I_B_TARGET -> setTargetBehavior(
                target = behavior.extension as Target,
                writer = writer
            )

            else -> throw UnspecifiedTypeException()
        }
    }

    private fun setSensorItem(sensorItem: SensorItem, writer: JSONWriter) {
        setItem(sensorItem, writer)

        writer.key("sensorId")
        writer.value(sensorItem.sensorId)
    }


    private fun setItem(item: BaseItem, writer: JSONWriter) {
        writer.key("name")
        writer.value(item.name)
        writer.key("id")
        writer.value(item.id)
        writer.key("type")
        writer.value(item.type)
    }

    private fun setFlatBehavior(flat: Flat, writer: JSONWriter) {
        writer.key("value")
        writer.value(flat.value)
    }

    private fun setLinearBehavior(linear: Linear, writer: JSONWriter) {
        writer.key("minTemp")
        writer.value(linear.minTemp)
        writer.key("maxTemp")
        writer.value(linear.maxTemp)
        writer.key("minFanSpeed")
        writer.value(linear.minFanSpeed)
        writer.key("maxFanSpeed")
        writer.value(linear.maxFanSpeed)
        writer.key("tempSensorId")
        writer.value(linear.tempSensorId)
    }


    private fun setTargetBehavior(target: Target, writer: JSONWriter) {
        writer.key("idleTemp")
        writer.value(target.idleTemp)
        writer.key("loadTemp")
        writer.value(target.loadTemp)
        writer.key("idleFanSpeed")
        writer.value(target.idleFanSpeed)
        writer.key("loadFanSpeed")
        writer.value(target.loadFanSpeed)
        writer.key("tempSensorId")
        writer.value(target.tempSensorId)
    }
}