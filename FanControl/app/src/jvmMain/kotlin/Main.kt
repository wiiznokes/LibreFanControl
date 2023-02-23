import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
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
                if ()



                app.onStop()
                exitApplication()
            }
        ) {
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



