package ui.utils

import model.item.BaseItem
import kotlin.random.Random

class NameIsTakenException : Exception()
class IndexHaveNameException : Exception()

class BlankException : Exception()


fun checkNameTaken(list: List<BaseItem>, name: String, index: Int) {
    if (name.isBlank())
        throw BlankException()
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


private fun isIdTaken(list: List<BaseItem>, id: Long): Boolean {
    return list.count { it.itemId == id } != 0
}

fun getAvailableId(list: List<BaseItem>): Long {
    val rand = Random(5)
    var id = rand.nextLong()
    while (isIdTaken(list, id)) {
        id = rand.nextLong()
    }
    return id
}
