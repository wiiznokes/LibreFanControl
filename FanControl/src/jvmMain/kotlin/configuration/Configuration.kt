package configuration

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.ItemType
import model.hardware.Control
import model.hardware.Sensor
import model.item.Behavior
import model.item.LibItem

class Configuration(
    private val _fanList: MutableStateFlow<SnapshotStateList<Sensor>> = State._fanList,
    private val _tempList: MutableStateFlow<SnapshotStateList<Sensor>> = State._tempList,
    private val _controlList: MutableStateFlow<SnapshotStateList<Control>> = State._controlList,


    private val _fanItemList: MutableStateFlow<SnapshotStateList<LibItem>> = State._fanItemList,
    private val _tempItemList: MutableStateFlow<SnapshotStateList<LibItem>> = State._tempItemList,
    private val _controlItemList: MutableStateFlow<SnapshotStateList<LibItem>>  = State._controlItemList,
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<Behavior>> = State._behaviorItemList
) {



    fun run(){

        _fanList.value.forEach{
            _fanItemList.value.add(
                LibItem(
                    name = it.libName,
                    isExpanded = false,
                    type = ItemType.FAN,
                    sensorName = it.libName,
                    sensorId = it.libId
                )
            )
        }

        _tempList.value.forEach{
            _tempItemList.value.add(
                LibItem(
                    name = it.libName,
                    isExpanded = false,
                    type = ItemType.FAN,
                    sensorName = it.libName,
                    sensorId = it.libId
                )
            )
        }

        _controlList.value.forEach{
            _controlItemList.value.add(
                LibItem(
                    name = it.libName,
                    isExpanded = false,
                    type = ItemType.FAN,
                    sensorName = it.libName,
                    sensorId = it.libId
                )
            )
        }
    }
}