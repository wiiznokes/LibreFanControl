package ui.screen.drawer.secondView

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import ui.screen.drawer.SettingType
import ui.utils.Resources

@Composable
fun settingHelp(
    settingState: MutableState<SettingType>
) {
    baseSecondView(
        title = Resources.getString("settings/help"),
        settingState = settingState
    ) {

    }
}