package model.item

import model.ItemType

data class BehaviorItem(
    override var name: String,
    override val type: ItemType,

    override var isExpanded: Boolean = false,
    val value: Int = 0
) : BaseItem