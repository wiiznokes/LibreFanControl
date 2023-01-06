import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.hardware.Sensor
import model.item.behavior.Behavior
import model.item.control.Control
import model.item.sensor.SensorItem
import settings.SettingsModel


data class SensorLists(
    val fanList: SnapshotStateList<Sensor> = mutableStateListOf(),
    val tempList: SnapshotStateList<Sensor> = mutableStateListOf()
)

class State {

    companion object {
        val controlList: SnapshotStateList<Control> = mutableStateListOf()
        val behaviorList: SnapshotStateList<Behavior> = mutableStateListOf()
        val fanItemList: SnapshotStateList<SensorItem> = mutableStateListOf()
        val tempItemList: SnapshotStateList<SensorItem> = mutableStateListOf()

        val sensorLists: SensorLists = SensorLists()

        val addItemExpanded: MutableStateFlow<Boolean> = MutableStateFlow(false)
        val editModeActivated: MutableStateFlow<Boolean> = MutableStateFlow(false)

        /**
         * used to know if controls has change recently, so logic class will recalculate
         * each controls, to know if we need to calculate each updateDelay
         */
        val controlsChange: MutableStateFlow<Boolean> = MutableStateFlow(false)

        val settings: MutableStateFlow<SettingsModel> = MutableStateFlow(SettingsModel())
    }
}


enum class Source {
    ADD,
    BODY
}