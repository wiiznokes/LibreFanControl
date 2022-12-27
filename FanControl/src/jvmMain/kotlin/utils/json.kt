package utils

import org.json.JSONObject

fun getString(path: String, rootJsonObject: JSONObject): String {
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

    return tempJSONObject.getString(realPath)
}


private fun getRealPath(input: String): String {
    return when (input) {
        "ct" -> "icon_content_description"
        "default" -> "default_value"
        else -> input
    }
}