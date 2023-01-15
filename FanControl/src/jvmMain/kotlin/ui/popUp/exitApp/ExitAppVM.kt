package ui.popUp.exitApp

import State
import kotlinx.coroutines.flow.MutableStateFlow
import settings.Settings
import settings.SettingsModel

class ExitAppVM(
    val settings: MutableStateFlow<SettingsModel> = State.settings
) {
    fun onButtonClick(
        exitOnCloseSet: Boolean,
        exitOnClose: Boolean
    ) {
        Settings.setSetting("exit_on_close_set", exitOnCloseSet)
        Settings.setSetting("exit_on_close", exitOnClose)
    }
}