package model.item

import model.ItemType

data class ControlItem(
    override var name: String = "",
    override var isExpanded: Boolean = false,
    override val type: ItemType,

    val sensorName: String,
    val sensorId: String,

    var visible: Boolean = true,

    var behaviorName: String = ""
) : BaseItem