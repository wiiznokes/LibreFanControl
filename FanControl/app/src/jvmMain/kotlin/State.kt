import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.hardware.HControl
import model.hardware.HFan
import model.hardware.HTemp
import model.item.BaseIBehavior
import model.item.IControl
import model.item.BaseITemp
import model.item.IFan
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