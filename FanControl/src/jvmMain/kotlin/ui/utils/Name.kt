package ui.utils

import model.item.BaseItem

class NameIsTakenException : Exception()
class IndexHaveNameException : Exception()


fun checkNameTaken(list: List<BaseItem>, name: String, index: Int) {
    if (list[index].name == name)
        throw IndexHaveNameException()

    if (isNameTaken(list, name))
        throw NameIsTakenException()
}

private fun isNameTaken(list: List<BaseItem>, name: String): Boolean {
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


private fun isIdTaken(list: List<BaseItem>, id: Int): Boolean {
    return list.count { it.id == id } != 0
}

fun getAvailableId(list: List<BaseItem>): Int {
    var id = 0
    while (isIdTaken(list, id)) {
        id++
    }
    return id
}
