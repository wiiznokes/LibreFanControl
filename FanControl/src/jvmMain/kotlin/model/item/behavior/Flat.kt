package model.item.behavior

data class Flat(
    override var value: Int = 50
) : BehaviorExtension {
    override fun copyI(value: Int): BehaviorExtension = copy(
        value = value
    )
}