import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import proto.ConfHelper
import proto.SettingsHelper
import ui.screen.home
import ui.screen.initDialogs
import ui.theme.fanControlTheme
import ui.utils.Resources


val app: Application = Application()

fun main() {
    val visible = mutableStateOf(true)
    application(
        exitProcessOnExit = true
    ) {
        Window(
            visible = visible.value,
            title = Resources.getString("title/app_name"),
            icon = Resources.getIcon("app/toys_fan48"),
            onCloseRequest = {
                CoroutineScope(Dispatchers.Default).launch {
                    println("stop: ${FState.ui.dialogExpanded.value}")

                    if (FState.settings.firstStart.value) {
                        println("LAUNCH_AT_START_UP")
                        FState.settings.firstStart.value = false
                        SettingsHelper.writeSettings(false)
                        FState.ui.dialogExpanded.value = UiState.Dialog.LAUNCH_AT_START_UP
                    }

                    while (FState.ui.dialogExpanded.value != UiState.Dialog.NONE) {
                        delay(200L)
                    }


                    if (!ConfHelper.isConfSave(FState.settings.confId.value, FState.ui.confName.value)) {
                        println("CONF_IS_NOT_SAVE")
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
                initDialogs()
            }
        }
    }
}