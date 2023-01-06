package model.item.sensor

import model.ItemType
import model.item.BaseItem


data class SensorItem(
    override var name: String,
    override val type: ItemType.SensorType,
    override val id: Long,

    val extension: SensorExtension
) : BaseItem

interface SensorExtension