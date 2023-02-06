package model.item.control

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import model.ItemType
import model.item.BaseI

class IControl(
    name: String,
    override val id: String,

    controlId: String? = null,
    isAuto: Boolean = true,
    behaviorId: String? = null

): BaseI {
    override val name: MutableState<String> = mutableStateOf(name)
    override val type: ItemType.ControlType = ItemType.ControlType.I_C_FAN
    val controlId: MutableState<String?> = mutableStateOf(controlId)
    val isAuto: MutableState<Boolean> = mutableStateOf(isAuto)
    val behaviorId: MutableState<String?> = mutableStateOf(behaviorId)
}