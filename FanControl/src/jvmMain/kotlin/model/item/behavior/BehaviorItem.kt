package model.item.behavior

import model.ItemType
import model.item.BaseItem

data class BehaviorItem(
    override var name: String,
    override val itemId: Long,
    override val type: ItemType.BehaviorType,

    override var isExpanded: Boolean = false,
    var flatBehavior: FlatBehavior? = null,
    val linearBehavior: LinearBehavior? = null
) : BaseItem