package model.item

import model.ItemType
import ui.utils.Resources

data class ControlItem(
    override var name: String,
    override val type: ItemType.ControlType,
    val sensorName: String,
    val sensorId: String,

    override var isExpanded: Boolean = false,
    var visible: Boolean = true,
    var behaviorName: String = Resources.getString("none")
) : BaseItem