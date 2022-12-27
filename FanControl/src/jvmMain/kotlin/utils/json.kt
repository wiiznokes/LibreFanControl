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


fun setString(path: String, key: String, value: String, rootJsonObject: JSONObject): JSONObject {

    val pathList = path.split("/")
    var realPath: String
    val jsonObjectsHistory = mutableListOf(rootJsonObject)

    // if "i" is not the last index, we need to find another JSONObject
    for (i in pathList.indices - 1) {
        realPath = getRealPath(pathList[i])
        jsonObjectsHistory.add(
            jsonObjectsHistory[jsonObjectsHistory.lastIndex].getJSONObject(realPath)
        )
    }
    realPath = getRealPath(pathList.last())

    jsonObjectsHistory[jsonObjectsHistory.lastIndex].put(key, value)

    // assemble the final JSONObject with the history
    val finalJSONObject = rootJsonObject

    for (i in 1 until jsonObjectsHistory.size) {

    }

    return rootJsonObject
}




fun setStringRec(path: List<String>, index: Int, obj: JSONObject, value: String): JSONObject {
    val realPath = getRealPath(path[index])

    return obj.put(
        realPath,
        when (index) {
            path.lastIndex -> { obj.put(realPath, value) }

            else -> setStringRec(
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

    return obj.put(
        realPath,
        when (index) {
            path.lastIndex -> { obj.remove(realPath) }

            else -> removeStringRec(
                path = path,
                index = index + 1,
                obj = obj.getJSONObject(realPath)
            )
        }
    )
}