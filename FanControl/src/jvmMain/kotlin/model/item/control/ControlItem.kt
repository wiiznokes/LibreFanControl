package model.item.control

import model.ItemType
import model.item.BaseItem

data class ControlItem(
    override var name: String,
    override val id: Long,
    override val type: ItemType.ControlType = ItemType.ControlType.I_C_FAN,

    var behaviorId: Long? = null,
    var isAuto: Boolean = true,
    var controlId: Long? = null,
) : BaseItem