package ui.screen.drawer.secondView

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import ui.screen.drawer.SettingType

@Composable
fun settingInfo(
    settingState: MutableState<SettingType>
) {
    baseSecondView(
        title = "Info",
        settingState = settingState
    ) {

    }
}