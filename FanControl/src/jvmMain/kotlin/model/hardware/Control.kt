package model.hardware

import model.ItemType

data class Control(
    override val libIndex: Int,
    override val libId: String,
    override val libName: String,
    override var value: Int = 0,
    override val type: ItemType.ControlType = ItemType.ControlType.FAN,
) : BaseHardware