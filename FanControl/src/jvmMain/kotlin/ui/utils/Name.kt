package ui.utils

import kotlin.random.Random

class NameIsTakenException : Exception()
class IndexHaveNameException : Exception()

class BlankException : Exception()


fun checkNameTaken(names: List<String>, name: String, index: Int? = null) {
    if (name.isBlank())
        throw BlankException()
    if (index != null) {
        if (names[index] == name)
            throw IndexHaveNameException()
    }

    if (isNameTaken(names, name))
        throw NameIsTakenException()
}

private fun isNameTaken(names: List<String>, name: String): Boolean {
    return names.count { it == name } != 0
}


fun getAvailableName(names: List<String>, prefix: String): String {
    var i = 1
    var name = "$prefix$i"
    while (isNameTaken(names, name)) {
        i++
        name = "$prefix$i"
    }
    return name
}


private fun isIdTaken(ids: List<Long>, id: Long): Boolean {
    return ids.count { it == id } != 0
}


fun getAvailableId(ids: List<Long>): Long {
    val rand = Random(5)
    var id = rand.nextLong()
    while (isIdTaken(ids, id)) {
        id = rand.nextLong()
    }
    return id
}
