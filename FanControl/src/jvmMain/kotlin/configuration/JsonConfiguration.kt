package configuration

import org.json.JSONObject
import org.json.JSONTokener
import org.json.JSONWriter
import java.io.File



private const val DIR_CONF = "./conf/"

private const val PREFIX_NEW_CONF = "config"
private const val SUFFIX_NEW_CONF = ".json"

class JsonConfiguration {
    companion object {

        private var _currentJsonObject: JSONObject? = null



        fun createAndWrite() {
            val f = File(DIR_CONF + "file1.json")

            val str = StringBuilder()

            val writer = JSONWriter(str)

            writer.`object`()
            writer.key("key")
            writer.value("hello")
            writer.key("keyyyyy")
            writer.value(_currentJsonObject)
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
}