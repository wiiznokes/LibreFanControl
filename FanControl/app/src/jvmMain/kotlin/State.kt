import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.hardware.*
import model.item.behavior.BaseIBehavior
import model.item.control.IControl
import model.item.sensor.BaseITemp
import model.item.sensor.IFan
import settings.Settings


object State {

    val iControls: SnapshotStateList<IControl> = mutableStateListOf()
    val iBehaviors: SnapshotStateList<BaseIBehavior> = mutableStateListOf()
    val iFans: SnapshotStateList<IFan> = mutableStateListOf()
    val iTemps: SnapshotStateList<BaseITemp> = mutableStateListOf()

    val hFans: SnapshotStateList<HFan> = mutableStateListOf()
    val hTemps: SnapshotStateList<HTemp> = mutableStateListOf()
    val hControls: SnapshotStateList<HControl> = mutableStateListOf()

    val addItemExpanded: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val editModeActivated: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val settings: Settings = Settings()

}


enum class Source {
    ADD,
    BODY
}