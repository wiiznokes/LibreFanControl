package ui.screen.drawer.settings

import androidx.compose.material3.Checkbox
import androidx.compose.runtime.MutableState
import com.example.settingSlidingWindows.SettingScope
import ui.utils.Resources


fun SettingScope.launchAtStartUp(
    launchAtStartUp: MutableState<Boolean>,
    onLaunchAtStartUpChange: (Boolean) -> Unit,
) {
    item(
        title = Resources.getString("settings/launch_at_startup"),
        advanceIconButton = {
            Checkbox(
                checked = launchAtStartUp.value,
                onCheckedChange = { onLaunchAtStartUpChange(it) }
            )
        },
        showTopLine = true
    )
}