package model.item

import androidx.compose.runtime.MutableState
import model.ItemType

class NameException : Exception()

interface BaseI {
    val name: MutableState<String>
    val type: ItemType
    val id: String

    companion object {
        /**
         * use for generate id
         */
        const val IControlPrefix = "iControl"
        const val IFlatPrefix = "iFlat"
        const val ILinearPrefix = "iLinear"
        const val ITargetPrefix = "iTarget"
        const val ITempPrefix = "iTemp"
        const val ICustomTempPrefix = "iCustomTemp"
        const val IFanPrefix = "iFan"


        fun getPrefix(id: String?): String? {
            if (id == null)
                return null

            return id.split("#").let {
                if(it.isEmpty()) "" else it[0]
            }
        }

        fun getAvailableId(list: List<String>, prefix: String): String =
            getAvailableString(list, prefix, "#")

        fun getAvailableName(list: List<String>, prefix: String): String  =
            getAvailableString(list, prefix, " #")


        private fun getAvailableString(list: List<String>, prefix: String, separator: String): String {
            var nb = 1
            var str = "$prefix$separator$nb"
            while (list.contains(str)) {
                nb++
                str = "$prefix$separator$nb"
            }
            return str
        }


        fun checkNameTaken(names: List<String>, name: String, index: Int? = null) {
            if (name.isBlank())
                throw NameException()

            when (names.count { it == name }) {
                0 -> return
                1 -> {
                    if (index == null)
                        throw NameException()

                    if (names[index] == name)
                        return
                }

                else -> throw NameException()
            }
        }

    }

}




