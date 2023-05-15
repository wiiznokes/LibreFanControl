import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.HControl
import model.hardware.HFan
import model.hardware.HTemp
import model.item.BaseIBehavior
import model.item.BaseITemp
import model.item.IControl
import model.item.IFan


const val DEBUG = true


enum class ServiceState {
    WAIT_OPEN,
    OPEN,
    ERROR
}

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

    var serviceState = mutableStateOf(ServiceState.WAIT_OPEN)

    var appVersion = ""
}


class UiState {
    val addItemExpanded = mutableStateOf(false)
    val editModeActivated = mutableStateOf(false)

    fun showError(error: CustomError, copy: Boolean = true) {

        var finalError = error
        if (copy) {
            if (error.copyContent == null) {
                finalError = error.copy(
                    copyContent = error.content
                )
            }
        }

        errorDialog.value = finalError
        dialogExpanded.value = Dialog.SHOW_ERROR
    }

    fun closeShowError() {
        errorDialog.value = null
        dialogExpanded.value = Dialog.NONE
    }

    enum class Dialog {
        NONE,
        NEW_CONF,
        SHOW_ERROR,
        LAUNCH_AT_START_UP,
        CONF_IS_NOT_SAVE
    }

    val dialogExpanded = mutableStateOf(Dialog.NONE)

    val errorDialog: MutableState<CustomError?> = mutableStateOf(null)
}


data class CustomError(
    val content: String,
    val copyContent: String? = null
)