import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.screen.home
import ui.theme.fanControlTheme
import ui.utils.Resources


fun main() {
    MainApp()
}

class MainApp {
    private val application: Application = Application().apply {
        onStart()
    }

    init {

        application(
            exitProcessOnExit = true
        ) {
            val settings = State.settings
            Window(
                visible = true,
                title = Resources.getString("title/app_name"),
                icon = Resources.getIcon("app/toys_fan48"),
                onCloseRequest = { exit() }
            ) {
                fanControlTheme(
                    settings.theme.value
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

