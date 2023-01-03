package model.item

import model.ItemType

data class ControlItem(
    override var name: String,
    override val itemId: Long,
    override val type: ItemType.ControlType = ItemType.ControlType.I_C_FAN,

    var visible: Boolean = true,
    var behaviorId: Long? = null,

    var value: Int = 0,
    var isAuto: Boolean = true,

    /*
        this variable is only used by
        the logic update class
    */
    var controlShouldBeSet: Boolean = false,

    val libIndex: Int,
    val libName: String,
    val libId: String,
) : BaseItem