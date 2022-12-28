package settings

import org.json.JSONObject
import org.json.JSONTokener
import utils.getJsonValue
import utils.removeStringRec
import utils.setStringRec
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

        fun getSetting(path: String): Any? {
            return getJsonValue(
                path = path,
                rootJsonObject = _paramsJsonObject
            )
        }


        fun setSetting(path: String, value: Any?) {
            updateVariable(
                setStringRec(
                    path = path.split("/"),
                    index = 0,
                    obj = _paramsJsonObject,
                    value = value
                )
            )
        }

        fun removeConfig(id: Long) {
            val path = "configList/$id"

            val newObj = removeStringRec(
                path = path.split("/"),
                index = 0,
                obj = _paramsJsonObject
            )

            updateVariable(newObj)
        }

        private fun updateVariable(newObj: JSONObject) {

            file.writeText(
                newObj.toString()
            )
            _paramsJsonObject = newObj
        }
    }
}