package configuration.write

import model.HardwareType
import model.hardware.BaseHardware
import org.json.JSONWriter

class WriteHardware {
    fun setHardware(hList: List<BaseHardware>, writer: JSONWriter, type: HardwareType) {
        writer.key("$type")
        writer.array()
        hList.forEach {
            writer.`object`()
            writer.key("id")
            writer.value(it.id)
            writer.key("type")
            writer.value(it.type)
            writer.endObject()
        }
        writer.endArray()
    }

}