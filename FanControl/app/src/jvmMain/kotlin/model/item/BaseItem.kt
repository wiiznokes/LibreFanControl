package model.item

import model.ItemType

interface BaseItem {
    var name: String
    val type: ItemType
    val id: Long
}