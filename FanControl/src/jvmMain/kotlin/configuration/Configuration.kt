package configuration


import State.iBehaviors
import State.hControls
import State.iFans
import State.hSensorsList
import State.iControls
import State.iTemps
import configuration.read.ReadHardware
import configuration.read.ReadItem
import configuration.write.WriteHardware
import configuration.write.WriteItem
import external.getOS
import model.HardwareType
import model.ItemType
import org.json.JSONObject
import org.json.JSONTokener
import org.json.JSONWriter
import settings.DIR_CONF
import java.io.File


private const val PREFIX_NEW_CONF = "config"
private const val SUFFIX_NEW_CONF = ".json"

class LoadConfigException : Exception()

class Configuration {
    companion object {
        private val readHardware = ReadHardware()
        private val readItem = ReadItem()
        private val writeHardware = WriteHardware()
        private val writeItem = WriteItem()

        /**
         * load configuration
         */
        fun loadConfig(
            configId: Long,
        ) {
            val file = getFile(configId)
            val string = file.bufferedReader().readText()
            val obj = JSONTokener(string).nextValue() as JSONObject

            readHardware.getSensors(
                hSensors = hSensorsList.hFans,
                array = obj.getJSONArray(HardwareType.SensorType.H_S_FAN.toString())
            )
            readHardware.getSensors(
                hSensors = hSensorsList.hTemps,
                array = obj.getJSONArray(HardwareType.SensorType.H_S_TEMP.toString())
            )

            iControls.clear()
            iBehaviors.clear()
            iFans.clear()
            iTemps.clear()

            readItem.getControls(
                iControls = iControls,
                array = obj.getJSONArray(ItemType.ControlType.I_C_FAN.toString())
            )
            readItem.getBehaviors(
                iBehaviors = iBehaviors,
                array = obj.getJSONArray(ItemType.BehaviorType.I_B_UNSPECIFIED.toString())
            )
            readItem.getSensors(
                iSensors = iFans,
                array = obj.getJSONArray(ItemType.SensorType.I_S_FAN.toString())
            )
            readItem.getSensors(
                iSensors = iTemps,
                array = obj.getJSONArray(ItemType.SensorType.I_S_TEMP.toString())
            )
        }


        fun deleteConfig(configId: Long) {
            val file = getFile(configId)
            file.delete()
        }

        fun saveConfig(
            configuration: ConfigurationModel,
        ) {
            val str = StringBuilder()
            val writer = JSONWriter(str)
            writer.`object`()

            writer.key("name")
            writer.value(configuration.name)
            writer.key("id")
            writer.value(configuration.id)
            writer.key("os")
            writer.value(getOS())

            writeHardware.setHardware(
                hList = hSensorsList.hFans,
                writer = writer,
                type = HardwareType.SensorType.H_S_FAN
            )
            writeHardware.setHardware(
                hList = hSensorsList.hTemps,
                writer = writer,
                type = HardwareType.SensorType.H_S_TEMP
            )
            writeHardware.setHardware(
                hList = hControls,
                writer = writer,
                type = HardwareType.ControlType.H_C_FAN
            )

            writeItem.setItems(
                iList = iControls,
                writer = writer,
                type = ItemType.ControlType.I_C_FAN
            )
            writeItem.setItems(
                iList = iBehaviors,
                writer = writer,
                type = ItemType.BehaviorType.I_B_UNSPECIFIED
            )
            writeItem.setItems(
                iList = iFans,
                writer = writer,
                type = ItemType.SensorType.I_S_FAN
            )
            writeItem.setItems(
                iList = iTemps,
                writer = writer,
                type = ItemType.SensorType.I_S_TEMP
            )
            writer.endObject()


            getFile(configuration.id).writeText(
                str.toString()
            )
        }

        private fun getFile(id: Long): File {
            val includeFolder = File(System.getProperty("compose.application.resources.dir"))

            return includeFolder.resolve(DIR_CONF + PREFIX_NEW_CONF + id + SUFFIX_NEW_CONF)
        }
    }
}