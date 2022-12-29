import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ui.screen.home
import ui.theme.fanControlTheme
import ui.utils.Resources


fun main() {

    val application = Application()
    application.onStart()

    application(
        exitProcessOnExit = true
    ) {
        Window(
            title = Resources.getString("title/app_name"),
            icon = Resources.getIcon("toys_fan"),
            state = rememberWindowState(),
            onCloseRequest = {
                application.onStop()
                (::exitApplication)()
            }

        ) {
            fanControlTheme{
                home()
            }
        }
    }

}


