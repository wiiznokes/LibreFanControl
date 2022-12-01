package model.behavior

import ui.ITEM_TYPE
import ui.ITEM_TYPE_SPECIFIC
import ui.ItemType


data class Flat(
    override val index: Int,
    override val id: String,
    override var name: String = "",
    override val type: ItemType = ItemType(
        itemType = ITEM_TYPE.BEHAVIOR,
        itemTypeSpecific = ITEM_TYPE_SPECIFIC.FLAT
    )
) : BaseBehavior