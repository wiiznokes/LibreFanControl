package configuration.write

import model.ItemType
import model.UnspecifiedTypeException
import model.item.BaseItem
import model.item.behavior.Behavior
import model.item.behavior.Flat
import model.item.behavior.Linear
import model.item.behavior.Target
import model.item.control.ControlItem
import model.item.sensor.CustomTemp
import model.item.sensor.Fan
import model.item.sensor.SensorItem
import model.item.sensor.Temp
import org.json.JSONWriter


class WriteItem {

    fun setItems(iList: List<BaseItem>, writer: JSONWriter, type: ItemType) {
        writer.key("$type")
        writer.array()
        iList.forEach {
            writer.`object`()
            when (type) {
                is ItemType.ControlType -> setControl(it as ControlItem, writer)
                is ItemType.BehaviorType -> setBehavior(it as Behavior, writer)
                is ItemType.SensorType -> setSensorItem(it as SensorItem, writer)
            }
            writer.endObject()
        }
        writer.endArray()
    }

    private fun setControl(control: ControlItem, writer: JSONWriter) {
        setItem(control, writer)

        writer.key("behaviorId")
        writer.value(control.behaviorId)
        writer.key("isAuto")
        writer.value(control.isAuto)
        writer.key("controlId")
        writer.value(control.controlId)
    }

    private fun setBehavior(behavior: Behavior, writer: JSONWriter) {
        setItem(behavior, writer)

        when (behavior.type) {
            ItemType.BehaviorType.I_B_FLAT -> setFlatBehavior(behavior.extension as Flat, writer)
            ItemType.BehaviorType.I_B_LINEAR -> setLinearBehavior(behavior.extension as Linear, writer)
            ItemType.BehaviorType.I_B_TARGET -> setTargetBehavior(behavior.extension as Target, writer)
            ItemType.BehaviorType.I_B_UNSPECIFIED -> throw UnspecifiedTypeException()
        }
    }

    private fun setSensorItem(sensorItem: SensorItem, writer: JSONWriter) {
        setItem(sensorItem, writer)

        when (sensorItem.type) {
            ItemType.SensorType.I_S_FAN -> setFan(sensorItem.extension as Fan, writer)
            ItemType.SensorType.I_S_TEMP -> setTemp(sensorItem.extension as Temp, writer)
            ItemType.SensorType.I_S_CUSTOM_TEMP -> setCustomTemp(sensorItem.extension as CustomTemp, writer)
            ItemType.SensorType.I_S_UNSPECIFIED -> throw UnspecifiedTypeException()
        }
    }


    private fun setTemp(temp: Temp, writer: JSONWriter) {
        writer.key("sensorId")
        writer.value(temp.sensorId)
    }

    private fun setFan(fan: Fan, writer: JSONWriter) {
        writer.key("sensorId")
        writer.value(fan.sensorId)
    }

    private fun setCustomTemp(customTemp: CustomTemp, writer: JSONWriter) {
        writer.key("customTempType")
        writer.value(customTemp.customTempType)
        writer.key("sensorIdList")
        writer.array()
        for (id in customTemp.sensorIdList)
            writer.value(id)
        writer.endArray()
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