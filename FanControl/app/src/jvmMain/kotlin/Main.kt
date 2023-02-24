import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import proto.SettingsHelper
import ui.screen.dialog.confNotSaveDialog
import ui.screen.home
import ui.screen.initDialogs
import ui.theme.fanControlTheme
import ui.utils.Resources


val app: Application = Application()

fun main() {
    application(
        exitProcessOnExit = true
    ) {
        Window(
            visible = true,
            title = Resources.getString("title/app_name"),
            icon = Resources.getIcon("app/toys_fan48"),
            onCloseRequest = {
                if (FState.settings.firstStart.value) {
                    FState.settings.firstStart.value = false
                    SettingsHelper.writeSettings(false)
                    FState.ui.dialogExpanded.value = UiState.Dialog.LAUNCH_AT_START_UP
                }

                while (FState.ui.dialogExpanded.value != UiState.Dialog.NONE) {
                    delay()
                }




                app.onStop()
                exitApplication()
            }
        ) {
            // tricks to have confName value all over the app
            FState.ui.confName.value = remember(FState.settings.confId.value) {
                when (val index = FState.settings.getIndexInfo()) {
                    null -> Resources.getString("common/none")
                    else -> FState.settings.confInfoList[index].name.value
                }
            }


            fanControlTheme(
                FState.settings.theme.value
            ) {
                app.onStart()

                home()

                confNotSaveDialog(
                    onQuit =  {
                        exitApplication()
                    },
                    confName = ""
                )
            }
        }
    }
}



