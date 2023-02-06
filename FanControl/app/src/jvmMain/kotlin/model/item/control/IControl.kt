package model.item.control

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import model.ItemType
import model.item.BaseI

class IControl(
    override val name: MutableState<String>,
    override val id: String
): BaseI{
    override val type: ItemType.ControlType = ItemType.ControlType.I_C_FAN

    val controlId: MutableState<String?> = mutableStateOf(null)
    val isAuto: MutableState<Boolean> = mutableStateOf(true)
    val behaviorId: MutableState<String?> = mutableStateOf(null)

}