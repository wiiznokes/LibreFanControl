package model.item

import model.ItemType

data class BehaviorItem(
    override var name: String = "",
    override var isExpanded: Boolean = false,
    override val type: ItemType,

    val value: Int = 0
) : BaseItem