package model.item

import androidx.compose.runtime.MutableState
import model.ItemType
import utils.Name
import utils.NameException

interface BaseI {
    val name: MutableState<String>
    val type: ItemType
    val id: String

    companion object {
        val IControlPrefix = "iControl"
        val IFlatPrefix = "iFlat"
        val ILinearPrefix = "iLinear"
        val ITargetPrefix = "iTarget"
        val ITempPrefix = "iTemp"
        val ICustomTempPrefix = "iCustomTemp"
        val IFanPrefix = "iFan"


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




