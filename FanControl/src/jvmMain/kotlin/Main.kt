import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import settings.Settings
import ui.screen.home
import ui.theme.fanControlTheme
import ui.utils.Resources


fun main() {
    MainApp()
}

class MainApp {
    private val application: Application

    init {
        Settings()
        application = Application().apply {
            onStart()
        }

        application(
            exitProcessOnExit = true
        ) {
            val settings = State.settings.collectAsState().value
            Window(
                visible = true,
                title = Resources.getString("title/app_name"),
                icon = Resources.getIcon("app/toys_fan48"),
                onCloseRequest = { exit() }
            ) {
                fanControlTheme(
                    settings.theme
                ) {
                    home()
                }

            }
        }
    }

    private fun ApplicationScope.exit() {
        application.onStop()
        exitApplication()
    }

}

