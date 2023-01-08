import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import settings.Settings
import ui.screen.home
import ui.theme.fanControlTheme
import ui.utils.Resources


fun main() {
    Settings()

    val application = Application().apply {
        onStart()
    }

    application(
        exitProcessOnExit = false
    ) {

        val visible = remember { mutableStateOf(true) }

        Window(
            visible = visible.value,
            title = Resources.getString("title/app_name"),
            icon = Resources.getIcon("app/toys_fan48"),
            onCloseRequest = {
                visible.value = false
                application.onClose()
            }

        ) {
            fanControlTheme(
                State.settings.collectAsState().value.theme
            ) {
                home()
            }


            Tray(
                icon = Resources.getIcon("app/toys_fan48"),
                tooltip = Resources.getString("title/app_name"),
                onAction = {
                    visible.value = true
                    application.onOpen()
                },
                menu = {
                    Item(
                        text = Resources.getString("common/open"),
                        onClick = {
                            visible.value = true
                            application.onOpen()
                        }
                    )
                    Separator()
                    Item(
                        text = Resources.getString("common/exit"),
                        onClick = {
                            application.onStop()
                            (::exitApplication)()
                        }
                    )
                }
            )
        }
    }
}