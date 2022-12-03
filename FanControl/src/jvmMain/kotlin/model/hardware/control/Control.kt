package model.hardware.control


data class Control(
    override val libIndex: Int,
    override val id: String,
    override var name: String = "",
    override var libName: String,
    override var isAuto: Boolean = true,
    override var value: Int = 0,
    override var maxValue: Int = 100,
    override var minValue: Int = 0,
    override var behaviorId: String? = null,
    override val specifyType: String,
    override val globalType: String,
    override var isExpanded: Boolean = false
) : BaseControl