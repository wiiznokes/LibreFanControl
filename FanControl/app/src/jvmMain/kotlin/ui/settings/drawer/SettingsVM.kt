package ui.settings.drawer

import FState
import proto.SettingsHelper
import ui.settings.Languages
import ui.settings.Settings
import ui.settings.Themes
import utils.OsSpecific

class SettingsVM(
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
        OsSpecific.os.changeServiceStartMode(launchAtStartUp)
    }

    fun removeService() {
        OsSpecific.os.removeService()
    }
}