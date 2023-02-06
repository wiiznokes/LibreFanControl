package model.item

import androidx.compose.runtime.MutableState
import model.ItemType

interface BaseI {
    val name: MutableState<String>
    val type: ItemType
    val id: String
}




