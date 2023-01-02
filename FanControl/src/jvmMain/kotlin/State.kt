import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.SettingsModel
import model.hardware.Sensor
import model.item.ControlItem
import model.item.SensorItem
import model.item.behavior.BehaviorItem


data class SensorLists(
    val fanList: SnapshotStateList<Sensor> = mutableStateListOf(),
    val tempList: SnapshotStateList<Sensor> = mutableStateListOf()
)

class State {

    companion object {
        val controlItemList: SnapshotStateList<ControlItem> = mutableStateListOf()
        val behaviorItemList: SnapshotStateList<BehaviorItem> = mutableStateListOf()
        val fanItemList: SnapshotStateList<SensorItem> = mutableStateListOf()
        val tempItemList: SnapshotStateList<SensorItem> = mutableStateListOf()

        val sensorLists: SensorLists = SensorLists()

        val addItemExpanded: MutableStateFlow<Boolean> = MutableStateFlow(false)
        val editModeActivated: MutableStateFlow<Boolean> = MutableStateFlow(false)

        val controlsChange: MutableStateFlow<Boolean> = MutableStateFlow(false)

        val settings: MutableStateFlow<SettingsModel> = MutableStateFlow(SettingsModel())
    }
}


enum class Source {
    ADD,
    BODY
}