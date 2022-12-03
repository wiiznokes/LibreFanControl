package ui.utils

import androidx.compose.ui.res.useResource
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener


enum class JsonObjectType {
    ARRAY,
    OBJECT,
    VALUE
}


private const val STRING_FILE_NAME = "strings.json"

private const val DEFAULT_LANGUE = "en"

class Resources {

    companion object {

        var langue: String = DEFAULT_LANGUE

        private val _rootJsonObject: JSONObject

        init {
            val string = useResource("values/$STRING_FILE_NAME") {
                it.bufferedReader().readText()
            }
            _rootJsonObject = JSONTokener(string).nextValue() as JSONObject
        }


        fun getString(id: String): String {
            return _rootJsonObject.getString(id)
        }


        private fun experimentalGetString(path: List<Pair<JsonObjectType, String>>): String {

            var tempJSONObject: JSONObject = _rootJsonObject
            var tempJsonArray: JSONArray? = null

            var lastUseObjectType: JsonObjectType = JsonObjectType.OBJECT

            for (p in path) {
                when (p.first) {
                    JsonObjectType.VALUE -> {
                        return tempJSONObject.getString(p.second)
                    }

                    JsonObjectType.ARRAY -> {
                        tempJsonArray = when (lastUseObjectType) {
                            JsonObjectType.ARRAY -> tempJsonArray!!.getJSONArray(p.second.toInt())
                            JsonObjectType.OBJECT -> tempJSONObject.getJSONArray(p.second)
                            else -> {
                                throw Exception("Resources: lastUseObjectType is a value")
                            }
                        }
                        lastUseObjectType = JsonObjectType.ARRAY
                    }

                    JsonObjectType.OBJECT -> {
                        tempJSONObject = when (lastUseObjectType) {
                            JsonObjectType.ARRAY -> tempJsonArray!!.getJSONObject(p.second.toInt())
                            JsonObjectType.OBJECT -> tempJSONObject.getJSONObject(p.second)
                            else -> {
                                throw Exception("Resources: lastUseObjectType is a value")
                            }
                        }
                        lastUseObjectType = JsonObjectType.OBJECT
                    }
                }
            }
            throw Exception("Value not found")
        }
    }
}