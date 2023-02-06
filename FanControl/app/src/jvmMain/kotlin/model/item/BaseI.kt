package model.item

import androidx.compose.runtime.MutableState
import model.ItemType

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

            return id.split("/")[0]
        }


        fun getAvailableString(list: List<String>, prefix: String): String {
            var nb = 1
            var str = "$prefix$nb"
            while (list.contains(str)) {
                nb++
                str = "$prefix$nb"
            }
            return str
        }

        class NameException : Exception()

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




