package settings

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.ConfigurationModel
import org.json.JSONObject
import org.json.JSONTokener
import utils.getJsonValue
import utils.setJsonValue
import java.io.File


private const val DIR_CONF = "./conf/"
private const val INIT_FILE_NAME = "settings.json"

class Settings {
    companion object {

        private var rootObj: JSONObject
        private val file: File = File(DIR_CONF + INIT_FILE_NAME)

        init {
            val string = file.bufferedReader().readText()
            rootObj = JSONTokener(string).nextValue() as JSONObject
        }

        fun getConfigList(
            configList: MutableStateFlow<SnapshotStateList<ConfigurationModel>>
        ) {
            val list = rootObj.getJSONObject("configList")

            for (key in list.keys()) {
                configList.update {
                    it.add(
                        ConfigurationModel(
                            id = key.toLong(),
                            name = list.getString(key)
                        )
                    )
                    it
                }
            }
        }

        fun <T> getSetting(path: String): T? {
            return getJsonValue(
                path = path,
                obj = rootObj
            )
        }


        fun setSetting(path: String, value: Any?) {
            updateVariable(
                setJsonValue(
                    path = path,
                    value = value,
                    obj = rootObj
                )
            )
        }

        fun removeConfig(id: Long) {
            val path = "configList/$id"
            updateVariable(
                setJsonValue(
                    path = path,
                    value = null,
                    obj = rootObj
                )
            )
        }

        private fun updateVariable(newObj: JSONObject) {

            file.writeText(
                newObj.toString()
            )
            rootObj = newObj
        }
    }
}