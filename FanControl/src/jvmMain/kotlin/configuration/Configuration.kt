package configuration


import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.ConfigurationModel
import model.ItemType
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
        fun loadConfig(
            configId: Long,
            controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>>,
            behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>>,
            fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>>,
            tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>>,

            fanList: List<Sensor>,
            tempList: List<Sensor>
        ) {
            val file = getFile(configId)
            val string = file.bufferedReader().readText()
            val obj = JSONTokener(string).nextValue() as JSONObject

            getControls(
                controlItemList = controlItemList,
                array = obj.getJSONArray(ItemType.ControlType.C_FAN.toString())
            )

            behaviorItemList.update {
                it.clear()
                it
            }
            getBehaviors(
                behaviorItemList = behaviorItemList,
                tempList = tempList,
                array = obj.getJSONArray(ItemType.BehaviorType.B_UNSPECIFIED.toString())
            )

            fanItemList.update {
                it.clear()
                it
            }
            getSensors(
                sensorItemList = fanItemList,
                sensorList = fanList,
                array = obj.getJSONArray(ItemType.SensorType.S_FAN.toString())
            )

            tempItemList.update {
                it.clear()
                it
            }
            getSensors(
                sensorItemList = tempItemList,
                sensorList = tempList,
                array = obj.getJSONArray(ItemType.SensorType.S_TEMP.toString())
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
            tempItemList: List<SensorItem>
        ) {
            val str = StringBuilder()
            val writer = JSONWriter(str)
            writer.`object`()

            writer.key("name")
            writer.value(configuration.name)
            writer.key("id")
            writer.value(configuration.id)

            setItems(
                itemList = controlItemList,
                writer = writer,
                type = ItemType.ControlType.C_FAN
            )
            setItems(
                itemList = behaviorItemList,
                writer = writer,
                type = ItemType.BehaviorType.B_UNSPECIFIED
            )
            setItems(
                itemList = fanItemList,
                writer = writer,
                type = ItemType.SensorType.S_FAN
            )
            setItems(
                itemList = tempItemList,
                writer = writer,
                type = ItemType.SensorType.S_TEMP
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