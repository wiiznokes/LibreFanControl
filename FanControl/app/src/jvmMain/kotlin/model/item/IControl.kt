package model.item

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import model.ItemType

class IControl(
    name: String,
    override val id: String,

    controlId: String? = null,
    isAuto: Boolean = true,
    iBehaviorId: String? = null,

    ) : BaseI {
    override val name: MutableState<String> = mutableStateOf(name)
    override val type: ItemType.ControlType = ItemType.ControlType.I_C_FAN
    val hControlId: MutableState<String?> = mutableStateOf(controlId)
    val isAuto: MutableState<Boolean> = mutableStateOf(isAuto)
    val iBehaviorId: MutableState<String?> = mutableStateOf(iBehaviorId)
}