package ui.screen.drawer.secondView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.screen.drawer.SettingType

@Composable
fun settingDonate(
    settingState: MutableState<SettingType>
) {
    baseSecondView(
        title = "Donate",
        settingState = settingState
    ) {

    }
}