package model.item

import model.ItemType

interface BaseItem {
    var name: String
    var isExpanded: Boolean
    val type: ItemType
}