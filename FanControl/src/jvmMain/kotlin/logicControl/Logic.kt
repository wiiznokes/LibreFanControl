package logicControl

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.ItemType
import model.hardware.Control
import model.hardware.Sensor
import model.item.ControlItem
import model.item.SensorItem
import model.item.behavior.BehaviorItem
import ui.utils.Resources

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
            it.visible && it.isActive && it.behaviorName != Resources.getString("none")
        }.forEach { control ->
            val behavior = _behaviorItemList.value.find { behavior ->
                behavior.name == control.behaviorName
            }
            when (behavior!!.type) {
                ItemType.BehaviorType.FLAT -> {
                    behavior.flatBehavior!!.value
                }

                ItemType.BehaviorType.LINEAR -> {

                }

                ItemType.BehaviorType.TARGET -> TODO()
            }
        }
    }


    fun findBehavior(behaviorName: String) {

    }
}