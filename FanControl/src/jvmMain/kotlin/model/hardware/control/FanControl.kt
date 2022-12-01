package model.hardware.control

import ui.ITEM_TYPE
import ui.ITEM_TYPE_SPECIFIC
import ui.ItemType

data class FanControl(
    override val index: Int,
    override val id: String,
    override var name: String? = null,
    override var libName: String,
    override var isAuto: Boolean = true,
    override var value: Int = 0,
    override var maxValue: Int = 100,
    override var minValue: Int = 0,
    override var behaviorId: String? = null,
    override val type: ItemType = ItemType(
        itemType = ITEM_TYPE.CONTROL,
        itemTypeSpecific = ITEM_TYPE_SPECIFIC.FAN_CONTROL
    )
) : BaseControl