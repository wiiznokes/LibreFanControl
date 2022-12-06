package model.item

import model.ItemType

data class SensorItem(
    override var name: String = "",
    override var isExpanded: Boolean = false,
    override val type: ItemType,

    var sensorName: String = "",
    var sensorId: String = ""
) : BaseItem