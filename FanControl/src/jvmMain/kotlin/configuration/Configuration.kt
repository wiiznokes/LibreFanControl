package configuration

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.ItemType
import model.hardware.Sensor
import model.item.ControlItem
import model.item.SensorItem
import model.item.behavior.BehaviorItem
import model.item.behavior.FlatBehavior
import model.item.behavior.LinearBehavior
import settings.Settings
import utils.getAvailableId

class Configuration(
    private val _fanList: MutableStateFlow<SnapshotStateList<Sensor>> = State._fanList,
    private val _tempList: MutableStateFlow<SnapshotStateList<Sensor>> = State._tempList,


    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList,
    private val _fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._fanItemList,
    private val _tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._tempItemList
) {
    // returns configId if it exists, otherwise null
    fun checkConfiguration(): Long? {
        val configId = Settings.getSetting("config")

        return if (configId == "none") {
            null
        } else
            configId.toLong()
    }

    fun loadConfig(configId: Long) {

    }

    fun init() {
        _behaviorItemList.update {
            _behaviorItemList.value.add(
                BehaviorItem(
                    name = "flat",
                    type = ItemType.BehaviorType.B_FLAT,
                    flatBehavior = FlatBehavior(),
                    itemId = getAvailableId(
                        ids = _behaviorItemList.value.map { item ->
                            item.itemId
                        }
                    )
                )
            )

            _behaviorItemList.value.add(
                BehaviorItem(
                    name = "linear",
                    type = ItemType.BehaviorType.B_LINEAR,
                    linearBehavior = LinearBehavior(),
                    itemId = getAvailableId(
                        ids = _behaviorItemList.value.map { item ->
                            item.itemId
                        }
                    )
                )
            )
            it
        }

        _fanList.value.forEach {
            _fanItemList.value.add(
                SensorItem(
                    name = it.libName,
                    type = ItemType.SensorType.S_FAN,
                    sensorName = it.libName,
                    sensorId = it.libId,
                    itemId = getAvailableId(
                        ids = _fanItemList.value.map { item ->
                            item.itemId
                        }
                    )
                )
            )
        }

        _tempList.value.forEach {
            _tempItemList.value.add(
                SensorItem(
                    name = it.libName,
                    type = ItemType.SensorType.S_TEMP,
                    sensorName = it.libName,
                    sensorId = it.libId,
                    itemId = getAvailableId(
                        ids = _tempItemList.value.map { item ->
                            item.itemId
                        }
                    )
                )
            )
        }
    }
}