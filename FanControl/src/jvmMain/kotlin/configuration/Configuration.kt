package configuration

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.ItemType
import model.hardware.Control
import model.hardware.Sensor
import model.item.BehaviorItem
import model.item.ControlItem
import model.item.SensorItem

class Configuration(
    private val _controlList: MutableStateFlow<SnapshotStateList<Control>> = State._controlList,
    private val _fanList: MutableStateFlow<SnapshotStateList<Sensor>> = State._fanList,
    private val _tempList: MutableStateFlow<SnapshotStateList<Sensor>> = State._tempList,


    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList,
    private val _fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._fanItemList,
    private val _tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._tempItemList
) {

    // check if configuration exist, return index of conf if true, else -1
    fun checkConfiguration(): Int {
        return -1
    }


    fun init() {

        _controlList.value.forEach {
            _controlItemList.value.add(
                ControlItem(
                    name = it.libName,
                    type = ItemType.FAN,
                    sensorName = it.libName,
                    sensorId = it.libId,
                )
            )
        }

        _behaviorItemList.value.add(
            BehaviorItem(
                name = "behavior1",
                type = ItemType.BEHAVIOR
            )
        )

        _fanList.value.forEach {
            _fanItemList.value.add(
                SensorItem(
                    name = it.libName,
                    type = ItemType.FAN,
                    sensorName = it.libName,
                    sensorId = it.libId,
                )
            )
        }

        _tempList.value.forEach {
            _tempItemList.value.add(
                SensorItem(
                    name = it.libName,
                    type = ItemType.FAN,
                    sensorName = it.libName,
                    sensorId = it.libId,
                )
            )
        }
    }
}