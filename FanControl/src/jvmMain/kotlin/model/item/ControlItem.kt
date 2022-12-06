package model.item

import model.ItemType

data class ControlItem(
    override var name: String = "",
    override var isExpanded: Boolean = false,
    override val type: ItemType,

    var sensorName: String? = null,
    var sensorId: String? = null,

    var visible: Boolean = true
) : BaseItem