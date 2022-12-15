package logicControl

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.hardware.Control
import model.hardware.Sensor
import model.item.ControlItem
import model.item.SensorItem
import model.item.behavior.BehaviorItem

class Logic(
    private val _controlList: MutableStateFlow<SnapshotStateList<Control>> = State._controlList,
    private val _fanList: MutableStateFlow<SnapshotStateList<Sensor>> = State._fanList,
    private val _tempList: MutableStateFlow<SnapshotStateList<Sensor>> = State._tempList,


    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList,
    private val _fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._fanItemList,
    private val _tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._tempItemList
) {

    fun update() {
        _controlItemList.value.filter {
            it.visible
        }
    }
}