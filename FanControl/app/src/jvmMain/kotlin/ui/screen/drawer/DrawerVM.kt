package ui.screen.drawer

import FState
import model.Languages
import model.Settings
import model.Themes
import proto.SettingsHelper
import ui.screen.drawer.settings.getStartMode
import java.io.File

class DrawerVM(
    val settings: Settings = FState.settings,
) {
    fun onUpdateDelay(delay: Int) {
        if (delay !in 1..100) {
            return
        }
        settings.updateDelay.value = delay
        SettingsHelper.writeSettings()
    }

    fun onLanguageChange(language: Languages) {
        settings.language.value = language
        SettingsHelper.writeSettings()
    }

    fun onThemeChange(theme: Themes) {
        settings.theme.value = theme
        SettingsHelper.writeSettings()
    }


    fun onLaunchAtStartUpChange(launchAtStartUp: Boolean) {
        val initScript = File(System.getProperty("compose.application.resources.dir"))
            .resolve("scripts/service/change_start_mode.ps1")
            .absolutePath

        val startMode = getStartMode(launchAtStartUp)

        val command = listOf(
            "powershell.exe",
            "-File",
            initScript,
            startMode
        )
        val res = ProcessBuilder(command)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()

        when (res) {
            0 -> {
                settings.launchAtStartUp.value = launchAtStartUp
                SettingsHelper.writeSettings(false)
            }
            3 -> FState.ui.dialogExpanded.value = UiState.Dialog.NEED_ADMIN

            else -> println("Error: can't set launchAtStartUp to $startMode")
        }
    }
}