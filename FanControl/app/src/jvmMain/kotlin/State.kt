import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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

    var settings: Settings = Settings()

    val ui  = UiState()

    fun showError(error: Exception) {
        if (!error.message.isNullOrBlank()) {
            ui.errorDialogContent.value = error.message.toString()
            ui.errorDialogExpanded.value = true
        }
    }
}

class UiState {
    val addItemExpanded = mutableStateOf(false)
    val editModeActivated = mutableStateOf(false)

    val saveConfDialogExpanded = mutableStateOf(false)
    val adminDialogExpanded = mutableStateOf(false)
    val errorDialogExpanded = mutableStateOf(false)
    val launchAtStartUpDialogExpanded = mutableStateOf(false)
    val confIsNotSaveDialogExpanded = mutableStateOf(false)

    val errorDialogContent = mutableStateOf("")
}