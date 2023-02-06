package ui.screen.drawer.settings

import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.settingSlidingWindows.SettingScope
import ui.theme.typography
import ui.utils.Resources

@OptIn(ExperimentalMaterial3Api::class)
fun SettingScope.exitOnClose(
    exitOnClose: Boolean,
    onExitChange: (Boolean) -> Unit
) {
    item(
        title = Resources.getString("settings/exit_on_close"),
        titleStyle = typography.titleSmall,
        advanceIconButton = {
            Checkbox(
                checked = exitOnClose,
                onCheckedChange = { onExitChange(it) }
            )
        },
        showTopLine = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
fun SettingScope.launchAtStartUp(
    launchAtStartUp: Boolean,
    onLaunchAtStartUpChange: (Boolean) -> Unit
) {
    item(
        title = Resources.getString("settings/launch_at_startup"),
        titleStyle = typography.titleSmall,
        advanceIconButton = {
            Checkbox(
                checked = launchAtStartUp,
                onCheckedChange = { onLaunchAtStartUpChange(it) }
            )
        }
    )
}