package ui

import androidx.compose.runtime.snapshots.SnapshotStateList
import model.behavior.BaseBehavior
import model.behavior.Flat
import model.hardware.control.FanControl
import model.hardware.sensor.FanSpeed
import model.hardware.sensor.Temp

data class UiStates(
    val fanList: SnapshotStateList<FanSpeed>,
    val tempList: SnapshotStateList<Temp>,
    val controlList: SnapshotStateList<FanControl>,
    val behaviorList: SnapshotStateList<Flat>,

    val addFanList: SnapshotStateList<FanSpeed>,
    val addTempList: SnapshotStateList<Temp>,
    val addControlList: SnapshotStateList<FanControl>,
    val addBehaviorList: SnapshotStateList<Flat>,
)





data class ItemType(
    val itemType: ITEM_TYPE,
    val itemTypeSpecific: ITEM_TYPE_SPECIFIC
)

enum class ITEM_TYPE {
    BEHAVIOR,
    CONTROL,
    SENSOR
}


enum class ITEM_TYPE_SPECIFIC {
    // behavior
    FLAT,

    // control
    FAN_CONTROL,

    // sensor
    FAN_SPEED,
    TEMP
}