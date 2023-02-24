package ui.screen.drawer

import FState
import model.Languages
import model.Settings
import model.Themes
import proto.SettingsHelper

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
        settings.launchAtStartUp.value = launchAtStartUp
        SettingsHelper.writeSettings()
    }
}