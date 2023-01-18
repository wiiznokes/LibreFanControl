import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import settings.Settings
import ui.popUp.exitApp.exitApp
import ui.screen.home
import ui.theme.fanControlTheme
import ui.utils.Resources


fun main() {
    MainApp()
}

class MainApp {
    private val application: Application

    private val appIsVisible = mutableStateOf(true)

    init {
        Settings()
        application = Application().apply {
            onStart()
        }

        application(
            exitProcessOnExit = false
        ) {
            val settings = State.settings.collectAsState().value
            val closeHasBeenClick = remember { mutableStateOf(false) }
            Window(
                visible = appIsVisible.value,
                title = Resources.getString("title/app_name"),
                icon = Resources.getIcon("app/toys_fan48"),
                onCloseRequest = {
                    if (!settings.exitOnCloseSet) closeHasBeenClick.value = true
                    else if (settings.exitOnClose) exit() else close()
                }
            ) {
                fanControlTheme(
                    settings.theme
                ) {
                    home()
                    exitApp(
                        closeHasBeenClick = closeHasBeenClick
                    ) {
                        if (it) exit() else close()
                    }
                }


                Tray(
                    icon = Resources.getIcon("app/toys_fan48"),
                    tooltip = Resources.getString("title/app_name"),
                    onAction = { open() },
                    menu = {
                        Item(
                            text = Resources.getString("common/open"),
                            onClick = { open() }
                        )
                        Separator()
                        Item(
                            text = Resources.getString("common/exit"),
                            onClick = {
                                exit()
                            }
                        )
                    }
                )
            }
        }
    }

    private fun open() {
        appIsVisible.value = true
        application.onOpen()
    }

    private fun ApplicationScope.exit() {
        application.onStop()
        exitApplication()
    }

    private fun close() {
        appIsVisible.value = false
        application.onClose()
    }
}

