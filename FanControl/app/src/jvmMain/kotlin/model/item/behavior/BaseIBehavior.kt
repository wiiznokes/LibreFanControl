package model.item.behavior

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import model.ItemType
import model.item.BaseI


interface BaseIBehavior : BaseI {
    override val name: MutableState<String>
    override val type: ItemType.BehaviorType
    override val id: String
    val value: MutableState<Int>
}


class IFlat(
    override val name: MutableState<String>,
    override val id: String
): BaseIBehavior{
    override val type: ItemType.BehaviorType = ItemType.BehaviorType.I_B_FLAT
    override val value: MutableState<Int> = mutableStateOf(0)
}


class ILinear(
    override val name: MutableState<String>,
    override val id: String
): BaseIBehavior{
    override val type: ItemType.BehaviorType = ItemType.BehaviorType.I_B_LINEAR
    override val value: MutableState<Int> = mutableStateOf(0)


    val minTemp: MutableState<Int> = mutableStateOf(25)
    val maxTemp: MutableState<Int> = mutableStateOf(60)
    val minFanSpeed: MutableState<Int> = mutableStateOf(10)
    val maxFanSpeed: MutableState<Int> = mutableStateOf(100)

    val hTempId: MutableState<String?> = mutableStateOf(null)
}


class ITarget(
    override val name: MutableState<String>,
    override val id: String
): BaseIBehavior{
    override val type: ItemType.BehaviorType = ItemType.BehaviorType.I_B_LINEAR
    override val value: MutableState<Int> = mutableStateOf(0)


    val idleTemp: MutableState<Int> = mutableStateOf(40)
    val loadTemp: MutableState<Int> = mutableStateOf(60)
    val idleFanSpeed: MutableState<Int> = mutableStateOf(50)
    val loadFanSpeed: MutableState<Int> = mutableStateOf(100)

    val hTempId: MutableState<String?> = mutableStateOf(null)
}