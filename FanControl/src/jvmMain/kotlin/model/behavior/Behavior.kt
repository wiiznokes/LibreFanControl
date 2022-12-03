package model.behavior


data class Behavior(
    override val id: String,
    override var name: String = "",
    override val specifyType: String,
    override val globalType: String, override var value: Int = 0,
    override var isExpanded: Boolean = false
) : BaseBehavior