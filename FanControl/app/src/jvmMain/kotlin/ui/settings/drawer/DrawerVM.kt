package ui.settings.drawer

import FState
import UiState
import proto.SettingsHelper
import ui.settings.Languages
import ui.settings.Settings
import ui.settings.Themes
import ui.settings.getStartMode
import java.io.File

class DrawerVM(
    val settings: Settings = FState.settings,
) {
    fun onUpdateDelay(delay: Int) {
        if (delay !in 1..100) {
            return
        }
        settings.updateDelay.value = delay
        SettingsHelper.writeSettings(true)
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
                SettingsHelper.writeSettings()
            }

            3 -> FState.ui.dialogExpanded.value = UiState.Dialog.NEED_ADMIN

            else -> println("Error: can't set launchAtStartUp to $startMode")
        }
    }
}