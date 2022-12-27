package params

import org.json.JSONObject
import org.json.JSONTokener
import org.json.JSONWriter
import utils.getString
import java.io.File



private const val DIR_CONF = "./conf/"
private const val INIT_FILE_NAME = "params.json"

class Params {
    companion object {

        private val _paramsJsonObject: JSONObject

        init {
            val file = File(DIR_CONF + INIT_FILE_NAME)
            val string = file.bufferedReader().readText()

            _paramsJsonObject = JSONTokener(string).nextValue() as JSONObject
        }

        fun getParam(path: String): String {
            return getString(
                path = path,
                rootJsonObject = _paramsJsonObject
            )
        }
    }
}