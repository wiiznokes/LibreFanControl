package configuration


import androidx.compose.runtime.snapshots.SnapshotStateList
import configuration.read.ReadHardware
import configuration.read.ReadItem
import configuration.write.WriteHardware
import configuration.write.WriteItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.ConfigurationModel
import model.HardwareType
import model.ItemType
import model.hardware.BaseHardware
import model.hardware.Sensor
import model.item.ControlItem
import model.item.SensorItem
import model.item.behavior.BehaviorItem
import org.json.JSONObject
import org.json.JSONTokener
import org.json.JSONWriter
import utils.DIR_CONF
import java.io.File


private const val PREFIX_NEW_CONF = "config"
private const val SUFFIX_NEW_CONF = ".json"

class Configuration {
    companion object {
        private val readHardware = ReadHardware()
        private val readItem = ReadItem()
        private val writeHardware = WriteHardware()
        private val writeItem = WriteItem()

        fun loadConfig(
            configId: Long,
            controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>>,
            behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>>,
            fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>>,
            tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>>,

            fanList: MutableStateFlow<SnapshotStateList<Sensor>>,
            tempList: MutableStateFlow<SnapshotStateList<Sensor>>
        ) {
            val file = getFile(configId)
            val string = file.bufferedReader().readText()
            val obj = JSONTokener(string).nextValue() as JSONObject

            readHardware.getSensors(
                sensorList = fanList,
                array = obj.getJSONArray(HardwareType.SensorType.H_S_FAN.toString())
            )
            readHardware.getSensors(
                sensorList = tempList,
                array = obj.getJSONArray(HardwareType.SensorType.H_S_TEMP.toString())
            )

            readItem.getControls(
                controlItemList = controlItemList,
                array = obj.getJSONArray(ItemType.ControlType.I_C_FAN.toString())
            )

            behaviorItemList.update {
                it.clear()
                it
            }
            readItem.getBehaviors(
                behaviorItemList = behaviorItemList,
                array = obj.getJSONArray(ItemType.BehaviorType.I_B_UNSPECIFIED.toString())
            )

            fanItemList.update {
                it.clear()
                it
            }
            readItem.getSensors(
                sensorItemList = fanItemList,
                array = obj.getJSONArray(ItemType.SensorType.I_S_FAN.toString())
            )

            tempItemList.update {
                it.clear()
                it
            }
            readItem.getSensors(
                sensorItemList = tempItemList,
                array = obj.getJSONArray(ItemType.SensorType.I_S_TEMP.toString())
            )
        }


        fun deleteConfig(configId: Long) {
            val file = getFile(configId)
            file.delete()
        }

        fun saveConfig(
            configuration: ConfigurationModel,
            controlItemList: List<ControlItem>,
            behaviorItemList: List<BehaviorItem>,
            fanItemList: List<SensorItem>,
            tempItemList: List<SensorItem>,

            fanList: List<BaseHardware>,
            tempList: List<BaseHardware>
        ) {
            val str = StringBuilder()
            val writer = JSONWriter(str)
            writer.`object`()

            writer.key("name")
            writer.value(configuration.name)
            writer.key("id")
            writer.value(configuration.id)

            writeHardware.setHardware(
                itemList = fanList,
                writer = writer,
                type = HardwareType.SensorType.H_S_FAN
            )
            writeHardware.setHardware(
                itemList = tempList,
                writer = writer,
                type = HardwareType.SensorType.H_S_TEMP
            )

            writeItem.setItems(
                itemList = controlItemList,
                writer = writer,
                type = ItemType.ControlType.I_C_FAN
            )
            writeItem.setItems(
                itemList = behaviorItemList,
                writer = writer,
                type = ItemType.BehaviorType.I_B_UNSPECIFIED
            )
            writeItem.setItems(
                itemList = fanItemList,
                writer = writer,
                type = ItemType.SensorType.I_S_FAN
            )
            writeItem.setItems(
                itemList = tempItemList,
                writer = writer,
                type = ItemType.SensorType.I_S_TEMP
            )
            writer.endObject()


            getFile(configuration.id).writeText(
                str.toString()
            )
        }

        private fun getFile(id: Long): File {
            return File(DIR_CONF + PREFIX_NEW_CONF + id + SUFFIX_NEW_CONF)
        }
    }
}