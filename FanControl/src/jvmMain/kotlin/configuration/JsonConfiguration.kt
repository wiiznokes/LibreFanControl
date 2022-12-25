package configuration

import org.json.JSONObject
import org.json.JSONTokener
import org.json.JSONWriter
import java.io.File


enum class JsonObjectType {
    ARRAY,
    OBJECT,
    VALUE
}

private const val DIR_CONF = "./conf/"

private const val INIT_FILE_NAME = "init.json"

private const val DEFAULT_LANGUE = "en"

class JsonConfiguration {
    private val _rootJsonObject: JSONObject

    init {
        val file = File(DIR_CONF + INIT_FILE_NAME)
        val string = file.bufferedReader().readText()

        _rootJsonObject = JSONTokener(string).nextValue() as JSONObject
    }

    fun getString(path: String): String {
        val list = path.split("/")


        var tempJSONObject: JSONObject = _rootJsonObject
        var i = 0
        var realPath: String

        // if i is not the last index, we need to find another JSONObject
        while (i < list.size - 1) {
            realPath = getRealPath(list[i])
            tempJSONObject = tempJSONObject.getJSONObject(realPath)
            i++
        }
        realPath = getRealPath(list[i])

        return tempJSONObject.getString(realPath)
    }

    private fun getRealPath(input: String): String {
        return when (input) {
            "ct" -> "icon_content_description"
            "default" -> "default_value"
            else -> input
        }
    }


    fun createAndWrite() {
        val f = File(DIR_CONF + "file1.json")

        val str = StringBuilder()

        val writer = JSONWriter(str)

        writer.`object`()
        writer.key("key")
        writer.value("hello")
        writer.key("keyyyyy")
        writer.value(_rootJsonObject)
        writer.endObject()



        println(str.toString())

        f.writeText(
            str.toString()
        )

    }

    fun write() {

        val str = StringBuilder()

        val writer = JSONWriter(str)
    }
}