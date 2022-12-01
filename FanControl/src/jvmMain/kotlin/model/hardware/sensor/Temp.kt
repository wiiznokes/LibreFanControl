package model.hardware.sensor

import ui.ITEM_TYPE
import ui.ITEM_TYPE_SPECIFIC
import ui.ItemType

data class Temp(
    override val index: Int,
    override val id: String,
    override var name: String? = null,
    override var libName: String = "",
    override var value: Int = 0,
    override var maxValue: Int = 100,
    override var minValue: Int = 0,
    override val type: ItemType = ItemType(
        itemType = ITEM_TYPE.SENSOR,
        itemTypeSpecific = ITEM_TYPE_SPECIFIC.TEMP
    )
) : BaseSensor