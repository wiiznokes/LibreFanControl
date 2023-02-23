import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.screen.home
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
                app.onStop()
                exitApplication()
            }
        ) {
            fanControlTheme(
                State.settings.theme.value
            ) {
                app.onStart()

                home()
            }

        }
    }
}



