package ui.settings.drawer

import FState
import Languages
import Settings
import Themes
import proto.SettingsHelper
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
        OsSpecific.os.changeStartModeService(launchAtStartUp)
    }

    fun onUninstallService() {
        OsSpecific.os.uninstallService()
    }

    fun onValueDisableControl(value: Int) {
        if (value < 2) {
            return
        }
        settings.valueDisableControl.value = value
        SettingsHelper.writeSettings(true)
    }
}