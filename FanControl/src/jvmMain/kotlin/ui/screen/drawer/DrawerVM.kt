package ui.screen.drawer

import State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import settings.Settings
import settings.SettingsModel
import ui.screen.drawer.settings.Languages
import ui.screen.drawer.settings.Themes


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

    fun onExitOnCloseChange(exitOnClose: Boolean) {
        settings.update {
            it.copy(
                exitOnClose = exitOnClose,
                exitOnCloseSet = true
            )
        }
        Settings.setSetting("exit_on_close_set", true)
        Settings.setSetting("exit_on_close", exitOnClose)
    }

    fun onLaunchAtStartUpChange(launchAtStartUp: Boolean) {
        settings.update {
            it.copy(
                launchAtStartUp = launchAtStartUp
            )
        }
        Settings.setSetting("launch_at_start_up", launchAtStartUp)
    }
}