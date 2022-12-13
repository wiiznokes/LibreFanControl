package ui.screen.addItem

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.ItemType
import model.hardware.Control
import model.item.ControlItem
import model.item.SensorItem
import model.item.behavior.BehaviorItem
import ui.utils.Resources

class AddItemViewModel(
    private val _controlList: MutableStateFlow<SnapshotStateList<Control>> = State._controlList,
    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList,
    private val _fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._fanItemList,
    private val _tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._tempItemList,
) {

    val controlList = _controlList.asStateFlow()
    val controlItemList = _controlItemList.asStateFlow()

    fun addControl(index: Int) {
        _controlItemList.update {
            _controlItemList.value[index] = _controlItemList.value[index].copy(
                visible = true
            )
            it
        }
    }


    fun addBehavior() {
        _behaviorItemList.update {
            it.add(
                BehaviorItem(
                    name = "Behavior",
                    type = ItemType.BehaviorType.FLAT
                )
            )
            it
        }
    }

    fun addFan() {
        _fanItemList.update {
            _fanItemList.value.add(
                SensorItem(
                    name = "fan1",
                    type = ItemType.SensorType.FAN,
                    sensorName = Resources.getString("none_item"),
                    sensorId = null
                )
            )
            it
        }
    }

    fun addTemp() {
        _tempItemList.update {
            _tempItemList.value.add(
                SensorItem(
                    name = "temp1",
                    type = ItemType.SensorType.TEMP,
                    sensorName = Resources.getString("none_item"),
                    sensorId = null
                )
            )
            it
        }
    }
}


