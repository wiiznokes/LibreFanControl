package configuration


import model.ConfigurationModel
import model.ItemType
import model.item.ControlItem
import model.item.SensorItem
import model.item.behavior.BehaviorItem
import org.json.JSONObject
import org.json.JSONTokener
import org.json.JSONWriter
import java.io.File


private const val DIR_CONF = "./conf/"

private const val PREFIX_NEW_CONF = "config"
private const val SUFFIX_NEW_CONF = ".json"

class JsonConfiguration {
    companion object {


        fun loadConfig(id: Long) {

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