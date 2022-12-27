package settings

import org.json.JSONObject
import org.json.JSONTokener
import org.json.JSONWriter
import utils.getString
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

        fun getSetting(path: String): String {
            return getString(
                path = path,
                rootJsonObject = _paramsJsonObject
            )
        }


        fun setSetting(path: String, value: String) {
            val newObj = setStringRec(
                path = path.split("/"),
                index = 0,
                obj = _paramsJsonObject,
                value = value
            )

            updateVariable(newObj)
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

            val str = StringBuilder()
            val writer = JSONWriter(str)

            writer.value(newObj)

            file.writeText(
                str.toString()
            )
            _paramsJsonObject = newObj
        }
    }
}