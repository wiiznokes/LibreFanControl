package ui.screen.drawer

import State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import settings.Settings
import settings.SettingsModel
import ui.screen.drawer.secondView.Languages
import ui.screen.drawer.secondView.Themes


class DrawerVM(
    private val settings: MutableStateFlow<SettingsModel> = State.settings
) {
    fun onUpdateDelay(delay: Int) {
        settings.update {
            it.copy(
                updateDelay = delay
            )
        }
        Settings.setSetting("updateDelay", delay)
    }

    fun onLanguageChange(language: Languages) {
        settings.update {
            it.copy(
                language = language
            )
        }
        Settings.setSetting("language", language)
    }

    fun onThemeChange(theme: Themes) {
        settings.update {
            it.copy(
                theme = theme
            )
        }
        Settings.setSetting("theme", theme)
    }
}