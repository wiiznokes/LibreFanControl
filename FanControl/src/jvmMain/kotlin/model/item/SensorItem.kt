package model.item

import model.ItemType
import ui.utils.Resources

data class SensorItem(
    override var name: String,
    override val type: ItemType,

    override var isExpanded: Boolean = false,
    var sensorName: String = Resources.getString("none_item"),
    var sensorId: String?
) : BaseItem