package ui.screen.drawer

import model.Languages
import model.Themes

class DrawerVM(
) {
    fun onUpdateDelay(delay: Int) {
        if (delay !in 1..100) {
            return
        }

    }

    fun onLanguageChange(language: Languages) {

    }

    fun onThemeChange(theme: Themes) {

    }

    fun onExitOnCloseChange(exitOnClose: Boolean) {

    }

    fun onLaunchAtStartUpChange(launchAtStartUp: Boolean) {

    }
}