import org.json.JSONObject
import org.json.JSONWriter
import org.junit.Test
import utils.getJsonValue

class JsonTest {
    @Test
    fun test() {
        assert(getJsonValue<String>("name", create()) == "myName")
    }


    private fun create(): JSONObject {
        val str = StringBuilder()
        val writer = JSONWriter(str)

        writer.`object`()
        writer.key("name")
        writer.value("myName")
        writer.endObject()


        println(str)
        return JSONObject(str.toString())
    }
}