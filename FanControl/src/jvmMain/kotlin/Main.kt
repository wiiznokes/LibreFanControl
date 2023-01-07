import androidx.compose.runtime.collectAsState
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
        exitProcessOnExit = true
    ) {
        Window(
            visible = true,
            title = Resources.getString("title/app_name"),
            icon = Resources.getIcon("app/toys_fan"),
            state = rememberWindowState(),
            onCloseRequest = {
                application.onStop()
                (::exitApplication)()
            }

        ) {
            fanControlTheme(
                State.settings.collectAsState().value.theme
            ) {
                home()
            }
        }
    }
}