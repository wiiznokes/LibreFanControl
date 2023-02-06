import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.hardware.Control
import model.hardware.Sensor
import model.item.behavior.Behavior
import model.item.control.IControl
import model.item.sensor.SensorI
import settings.SettingsModel


object State {

    val iControls: SnapshotStateList<IControl> = mutableStateListOf()
    val iBehaviors: SnapshotStateList<Behavior> = mutableStateListOf()
    val iFans: SnapshotStateList<SensorI> = mutableStateListOf()
    val iTemps: SnapshotStateList<SensorI> = mutableStateListOf()

    val hFans: SnapshotStateList<Sensor> = mutableStateListOf()
    val hTemps: SnapshotStateList<Sensor> = mutableStateListOf()
    val hControls: SnapshotStateList<Control> = mutableStateListOf()

    val addItemExpanded: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val editModeActivated: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val settings: MutableStateFlow<SettingsModel> = MutableStateFlow(SettingsModel())

}


enum class Source {
    ADD,
    BODY
}