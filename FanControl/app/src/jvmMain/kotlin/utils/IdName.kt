package utils

import kotlin.random.Random

class NameException : Exception()

class Name {
    companion object {
        fun checkNameTaken(names: List<String>, name: String, index: Int? = null) {
            if (name.isBlank())
                throw NameException()

            if (isNameTaken(names, name)) {
                // check if it's not the index witch have the name
                if (index != null) {
                    if (names[index] == name)
                        return
                }
                throw NameException()
            }
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

        private fun isNameTaken(names: List<String>, name: String): Boolean =
            names.count { it == name } != 0

    }
}


class Id {

    companion object {

        fun getAvailableId(ids: List<String>, prefix: String): String {
            var nb = 0
            var id = "$prefix$nb"
            while (ids.contains(id)) {
                nb++
                id = "$prefix$nb"
            }
            return id
        }

    }
}