package model.item

import model.ItemType

data class SensorItem(
    override var name: String,
    override val type: ItemType.SensorType,
    override val id: Long,

    var sensorId: Long? = null
) : BaseItem