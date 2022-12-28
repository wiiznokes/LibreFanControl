package utils

import org.json.JSONObject

fun getJsonValue(path: String, rootJsonObject: JSONObject): Any? {
    val list = path.split("/")


    var tempJSONObject: JSONObject = rootJsonObject
    var i = 0
    var realPath: String

    // if "i" is not the last index, we need to find another JSONObject
    while (i < list.size - 1) {
        realPath = getRealPath(list[i])
        tempJSONObject = tempJSONObject.getJSONObject(realPath)
        i++
    }
    realPath = getRealPath(list[i])

    return tempJSONObject.get(realPath)
}


private fun getRealPath(input: String): String {
    return when (input) {
        "ct" -> "icon_content_description"
        "default" -> "default_value"
        else -> input
    }
}

fun setStringRec(path: List<String>, index: Int, obj: JSONObject, value: String): JSONObject {
    val realPath = getRealPath(path[index])

    return obj.put(
        realPath,
        when (index) {
            path.lastIndex -> value

            else ->
                setStringRec(
                    path = path,
                    index = index + 1,
                    obj = obj.getJSONObject(realPath),
                    value = value
                )

        }
    )
}

fun removeStringRec(path: List<String>, index: Int, obj: JSONObject): JSONObject {
    val realPath = getRealPath(path[index])

    return when (index) {
        path.lastIndex -> obj.apply { remove(realPath) }

        else -> obj.put(
            realPath,
            removeStringRec(
                path = path,
                index = index + 1,
                obj = obj.getJSONObject(realPath)
            )
        )
    }
}