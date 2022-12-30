package model.item

import model.ItemType
import ui.utils.Resources

data class SensorItem(
    override var name: String,
    override val type: ItemType.SensorType,
    override val itemId: Long,

    override var isExpanded: Boolean = false,
    var sensorId: Long? = null
) : BaseItem