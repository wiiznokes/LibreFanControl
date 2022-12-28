package settings

import org.json.JSONObject
import org.json.JSONTokener
import utils.getJsonValue
import utils.setJsonValue
import java.io.File


private const val DIR_CONF = "./conf/"
private const val INIT_FILE_NAME = "settings.json"

class Settings {
    companion object {

        private var _paramsJsonObject: JSONObject
        private val file: File = File(DIR_CONF + INIT_FILE_NAME)

        init {
            val string = file.bufferedReader().readText()

            _paramsJsonObject = JSONTokener(string).nextValue() as JSONObject
        }

        fun <T> getSetting(path: String): T? {
            return getJsonValue(
                path = path,
                obj = _paramsJsonObject
            )
        }


        fun setSetting(path: String, value: Any?) {
            updateVariable(
                setJsonValue(
                    path = path,
                    value = value,
                    obj = _paramsJsonObject
                )
            )
        }

        fun removeConfig(id: Long) {
            val path = "configList/$id"
            updateVariable(
                setJsonValue(
                    path = path,
                    value = null,
                    obj = _paramsJsonObject
                )
            )
        }

        private fun updateVariable(newObj: JSONObject) {

            file.writeText(
                newObj.toString()
            )
            _paramsJsonObject = newObj
        }
    }
}