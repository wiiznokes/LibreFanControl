package configuration.write

import model.HardwareType
import model.hardware.BaseHardware
import model.hardware.Sensor
import org.json.JSONWriter

class WriteHardware {
    fun setHardware(itemList: List<BaseHardware>, writer: JSONWriter, type: HardwareType) {
        writer.key("$type")
        writer.array()
        itemList.forEach {
            writer.`object`()
            when (type) {
                is HardwareType.SensorType -> setSensor(it as Sensor, writer)
            }
            writer.endObject()
        }
        writer.endArray()
    }


    private fun setSensor(sensor: Sensor, writer: JSONWriter) {
        writer.key("libId")
        writer.value(sensor.libId)

        writer.key("id")
        writer.value(sensor.id)
    }
}