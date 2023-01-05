package utils

import org.json.JSONObject

const val DIR_CONF = "./conf/"


fun <T> getJsonValue(path: String, obj: JSONObject): T? {
    val res = getJsonValueRec(
        path = path.split("/"),
        obj = obj,
        index = 0
    )
    return if (res == JSONObject.NULL)
        null
    else
        @Suppress("UNCHECKED_CAST")
        res as T
}

fun setJsonValue(path: String, value: Any?, obj: JSONObject): JSONObject {
    return setJsonValueRec(
        path = path.split("/"),
        value = value,
        obj = obj,
        index = 0
    )
}

private fun getJsonValueRec(path: List<String>, obj: JSONObject, index: Int): Any? {
    val realPath = getRealPath(path[index])

    return when (index) {
        path.lastIndex -> obj.get(realPath)

        else -> getJsonValueRec(
            path = path,
            obj = obj.getJSONObject(realPath),
            index = index + 1
        )
    }
}

private fun setJsonValueRec(path: List<String>, value: Any?, obj: JSONObject, index: Int): JSONObject {
    val realPath = getRealPath(path[index])

    return obj.put(
        realPath,
        when (index) {
            path.lastIndex -> value

            else ->
                setJsonValueRec(
                    path = path,
                    value = value,
                    obj = obj.getJSONObject(realPath),
                    index = index + 1
                )

        }
    )
}

private fun getRealPath(input: String): String {
    return when (input) {
        "ct" -> "icon_content_description"
        "default" -> "default_value"
        "trans" -> "transition"
        else -> input
    }
}