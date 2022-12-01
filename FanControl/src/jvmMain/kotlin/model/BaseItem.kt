package model

import ui.ItemType

interface BaseItem {
    val index: Int
    val id: String
    val type : ItemType
}