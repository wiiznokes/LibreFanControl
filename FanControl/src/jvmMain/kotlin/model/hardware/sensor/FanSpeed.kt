package model.hardware.sensor

import ui.ITEM_TYPE
import ui.ITEM_TYPE_SPECIFIC
import ui.ItemType

data class FanSpeed(
    override val index: Int,
    override val id: String,
    override var libName: String = "",
    override var value: Int = 0,
    override var maxValue: Int = 4000,
    override var minValue: Int = 0,
    override var name: String? = null,
    override val type: ItemType = ItemType(
        itemType = ITEM_TYPE.SENSOR,
        itemTypeSpecific = ITEM_TYPE_SPECIFIC.FAN_SPEED
    )
): BaseSensor