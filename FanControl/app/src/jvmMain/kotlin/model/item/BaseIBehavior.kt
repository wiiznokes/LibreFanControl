package model.item

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import model.ItemType


interface BaseIBehavior : BaseI {
    override val type: ItemType.BehaviorType
    val value: MutableState<Int>
}


class IFlat(
    name: String,
    override val id: String,
    value: Int = 0,
) : BaseIBehavior {
    override val name: MutableState<String> = mutableStateOf(name)
    override val type: ItemType.BehaviorType = ItemType.BehaviorType.I_B_FLAT

    override val value: MutableState<Int> = mutableStateOf(value)

}


class ILinear(
    name: String,
    override val id: String,
    value: Int = 0,


    minTemp: Int = 25,
    maxTemp: Int = 60,
    minFanSpeed: Int = 10,
    maxFanSpeed: Int = 100,

    hTempId: String? = null,
) : BaseIBehavior {
    override val name: MutableState<String> = mutableStateOf(name)
    override val type: ItemType.BehaviorType = ItemType.BehaviorType.I_B_LINEAR

    override val value: MutableState<Int> = mutableStateOf(value)

    val minTemp: MutableState<Int> = mutableStateOf(minTemp)
    val maxTemp: MutableState<Int> = mutableStateOf(maxTemp)
    val minFanSpeed: MutableState<Int> = mutableStateOf(minFanSpeed)
    val maxFanSpeed: MutableState<Int> = mutableStateOf(maxFanSpeed)

    val hTempId: MutableState<String?> = mutableStateOf(hTempId)
}


class ITarget(
    name: String,
    override val id: String,
    value: Int = 0,

    idleTemp: Int = 40,
    loadTemp: Int = 60,
    idleFanSpeed: Int = 50,
    loadFanSpeed: Int = 100,

    hTempId: String? = null,

    ) : BaseIBehavior {
    override val name: MutableState<String> = mutableStateOf(name)
    override val type: ItemType.BehaviorType = ItemType.BehaviorType.I_B_TARGET

    override val value: MutableState<Int> = mutableStateOf(value)

    val idleTemp: MutableState<Int> = mutableStateOf(idleTemp)
    val loadTemp: MutableState<Int> = mutableStateOf(loadTemp)
    val idleFanSpeed: MutableState<Int> = mutableStateOf(idleFanSpeed)
    val loadFanSpeed: MutableState<Int> = mutableStateOf(loadFanSpeed)

    val hTempId: MutableState<String?> = mutableStateOf(hTempId)
}