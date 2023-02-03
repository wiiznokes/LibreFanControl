import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import model.hardware.Control
import model.hardware.Sensor
import model.item.behavior.Behavior
import model.item.control.ControlItem
import model.item.sensor.SensorItem
import settings.SettingsModel


data class SensorLists(
    val hFans: SnapshotStateList<Sensor> = mutableStateListOf(),
    val hTemps: SnapshotStateList<Sensor> = mutableStateListOf()
)

object State {

    val iControls: SnapshotStateList<ControlItem> = mutableStateListOf()
    val iBehaviors: SnapshotStateList<Behavior> = mutableStateListOf()
    val iFans: SnapshotStateList<SensorItem> = mutableStateListOf()
    val iTemps: SnapshotStateList<SensorItem> = mutableStateListOf()

    val hSensorsList: SensorLists = SensorLists()
    val hControls: SnapshotStateList<Control> = mutableStateListOf()

    val addItemExpanded: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val editModeActivated: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val settings: MutableStateFlow<SettingsModel> = MutableStateFlow(SettingsModel())

}


enum class Source {
    ADD,
    BODY
}