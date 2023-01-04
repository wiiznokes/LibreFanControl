package model.item.behavior

data class Flat(
    override var value: Int = 50
) : BehaviorExtension {
    override fun copy_(value: Int): BehaviorExtension = copy(
        value = value
    )
}