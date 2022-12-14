package ui.utils

import model.item.BaseItem

fun isNameTaken(list: List<BaseItem>, name: String): Boolean {
    return list.count { it.name == name } != 0
}

fun getAvailableName(list: List<BaseItem>, prefix: String): String {
    var i = 1
    var name = "$prefix$i"
    while (isNameTaken(list, name)) {
        i++
        name = "$prefix$i"
    }
    return name
}