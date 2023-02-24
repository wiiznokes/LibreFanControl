import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.Settings
import model.hardware.HControl
import model.hardware.HFan
import model.hardware.HTemp
import model.item.BaseIBehavior
import model.item.BaseITemp
import model.item.IControl
import model.item.IFan


object FState {

    val hControls: SnapshotStateList<HControl> = mutableStateListOf()
    val hTemps: SnapshotStateList<HTemp> = mutableStateListOf()
    val hFans: SnapshotStateList<HFan> = mutableStateListOf()

    val iControls: SnapshotStateList<IControl> = mutableStateListOf()
    val iBehaviors: SnapshotStateList<BaseIBehavior> = mutableStateListOf()
    val iTemps: SnapshotStateList<BaseITemp> = mutableStateListOf()
    val iFans: SnapshotStateList<IFan> = mutableStateListOf()

    var settings: Settings = Settings()

    val ui = UiState()

}


class UiState {
    val addItemExpanded = mutableStateOf(false)
    val editModeActivated = mutableStateOf(false)

    fun showError(error: Exception) {
        if (!error.message.isNullOrBlank()) {
            errorDialogContent.value = error.stackTrace.contentToString()
            dialogExpanded.value = Dialog.SHOW_ERROR
        }
    }

    fun closeShowError() {
        errorDialogContent.value = ""
        dialogExpanded.value = Dialog.NONE
    }

    enum class Dialog {
        NONE,
        NEW_CONF,
        NEED_ADMIN,
        SHOW_ERROR,
        LAUNCH_AT_START_UP,
        CONF_IS_NOT_SAVE
    }

    val dialogExpanded = mutableStateOf(Dialog.NONE)

    val errorDialogContent = mutableStateOf("")

    val confName = mutableStateOf("")
}