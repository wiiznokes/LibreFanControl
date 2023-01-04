package model.item.behavior

import model.ItemType
import model.item.BaseItem

data class Behavior(
    override var name: String,
    override val itemId: Long,
    override val type: ItemType.BehaviorType,

    val extension: BehaviorExtension,
) : BaseItem


interface BehaviorExtension {
    var value: Int

    fun copy_(value: Int): BehaviorExtension
}