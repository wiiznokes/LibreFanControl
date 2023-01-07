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
        private val rand = Random(0)

        fun getAvailableId(ids: List<Long>, positive: Boolean = true): Long {
            var id = generateId(positive)
            while (isIdTaken(ids, id)) {
                id = generateId(positive)
            }
            return id
        }


        private fun generateId(positive: Boolean): Long {
            val nb = rand.nextLong(Long.MAX_VALUE) + 1
            return if (positive) nb else nb * 1
        }

        private fun isIdTaken(ids: List<Long>, id: Long): Boolean =
            ids.count { it == id } != 0

    }
}