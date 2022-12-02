package ui

import androidx.compose.runtime.snapshots.SnapshotStateList
import model.behavior.Behavior
import model.hardware.control.Control
import model.hardware.sensor.Fan
import model.hardware.sensor.Temp

data class UiStates(
    val fanList: SnapshotStateList<Fan>,
    val tempList: SnapshotStateList<Temp>,
    val controlList: SnapshotStateList<Control>,
    val behaviorList: SnapshotStateList<Behavior>,

    val addFanList: SnapshotStateList<Fan>,
    val addTempList: SnapshotStateList<Temp>,
    val addControlList: SnapshotStateList<Control>,
    val addBehaviorList: SnapshotStateList<Behavior>,
)


class FlagGlobalItemType {
    companion object {
        // behavior
        const val FLAT_BEHAVIOR = "FLAT_BEHAVIOR"

        // control
        const val FAN_CONTROL = "FAN_CONTROL"

        // sensor
        const val FAN_SENSOR = "FAN_SENSOR"
        const val TEMP_SENSOR = "TEMP_SENSOR"
    }
}

class FlagSpecifyItemType {
    companion object {
        // behavior
        const val FLAT_BEHAVIOR = "FLAT_BEHAVIOR"

        // control
        const val FAN_CONTROL = "FAN_CONTROL"

        // sensor
        const val FAN_SENSOR = "FAN_SENSOR"
        const val TEMP_SENSOR = "TEMP_SENSOR"
    }
}

