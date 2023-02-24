package ui.screen.drawer.settings

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.MutableState
import com.example.settingSlidingWindows.SettingScope
import ui.theme.LocalColors
import ui.utils.Resources


fun getStartMode(launchAtStartUp: Boolean): String {
    return when (launchAtStartUp) {
        true -> "Automatic"
        false -> "Manual"
    }
}

fun SettingScope.launchAtStartUp(
    launchAtStartUp: MutableState<Boolean>,
    onLaunchAtStartUpChange: (Boolean) -> Unit,
) {
    item(
        title = Resources.getString("settings/launch_at_start_up"),
        advanceIconButton = {
            Checkbox(
                checked = launchAtStartUp.value,
                onCheckedChange = {
                    onLaunchAtStartUpChange(it)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = LocalColors.current.input,
                    uncheckedColor = LocalColors.current.onInactiveInput,
                    checkmarkColor = LocalColors.current.onInput,
                )
            )
        },
        showTopLine = true
    )
}