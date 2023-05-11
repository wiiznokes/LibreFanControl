import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import proto.ConfHelper
import proto.SettingsHelper
import ui.container.home
import ui.theme.fanControlTheme
import utils.*


val app: Application = Application()

fun main() {

    if (getOS() == OS.linux) {
        // need admin to write in /etc dir
        if (!OsSpecific.os.isAdmin()) {
            throw Exception("This app need admin privilege. Please retry with sudo.")
        }
    }


    val visible = mutableStateOf(true)

    app.onStart()

    application(
        exitProcessOnExit = true
    ) {
        Window(
            visible = visible.value,
            title = Resources.getString("title/app_name"),
            icon = Resources.getIcon("app/toys_fan48"),
            onCloseRequest = {
                CoroutineScope(Dispatchers.Default).launch {

                    if (FState.settings.firstStart.value) {
                        FState.settings.firstStart.value = false
                        SettingsHelper.writeSettings()
                        if (!FState.settings.launchAtStartUp.value)
                            FState.ui.dialogExpanded.value = UiState.Dialog.LAUNCH_AT_START_UP
                    }

                    while (FState.ui.dialogExpanded.value != UiState.Dialog.NONE) {
                        delay(200L)
                    }



                    if (!ConfHelper.isConfSave(FState.settings.confId.value)) {
                        FState.ui.dialogExpanded.value = UiState.Dialog.CONF_IS_NOT_SAVE
                    }

                    while (FState.ui.dialogExpanded.value != UiState.Dialog.NONE) {
                        delay(200L)
                    }
                    visible.value = false

                    app.onStop()
                    exitApplication()
                }
            }
        ) {
            fanControlTheme(
                FState.settings.theme.value
            ) {
                home()
                initDialogs()
            }
        }
    }
}