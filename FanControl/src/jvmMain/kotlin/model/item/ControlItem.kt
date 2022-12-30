package model.item

import model.ItemType

data class ControlItem(
    override var name: String,
    override val itemId: Long,
    override val type: ItemType.ControlType = ItemType.ControlType.I_C_FAN,

    override var isExpanded: Boolean = false,
    var visible: Boolean = true,
    var behaviorId: Long? = null,

    var value: Int = 0,
    val isAuto: Boolean = true,

    val libIndex: Int,
    val libName: String,
    val libId: String,
) : BaseItem