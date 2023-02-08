import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.Settings
import model.hardware.HControl
import model.hardware.HFan
import model.hardware.HTemp
import model.item.BaseIBehavior
import model.item.BaseITemp
import model.item.IControl
import model.item.IFan


object State {

    val hControls: SnapshotStateList<HControl> = mutableStateListOf()
    val hTemps: SnapshotStateList<HTemp> = mutableStateListOf()
    val hFans: SnapshotStateList<HFan> = mutableStateListOf()

    val iControls: SnapshotStateList<IControl> = mutableStateListOf()
    val iBehaviors: SnapshotStateList<BaseIBehavior> = mutableStateListOf()
    val iTemps: SnapshotStateList<BaseITemp> = mutableStateListOf()
    val iFans: SnapshotStateList<IFan> = mutableStateListOf()


    val addItemExpanded: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val editModeActivated: MutableStateFlow<Boolean> = MutableStateFlow(false)

    var settings: Settings = Settings()

}


enum class Source {
    ADD,
    BODY
}