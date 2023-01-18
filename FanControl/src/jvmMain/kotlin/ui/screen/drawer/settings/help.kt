package ui.screen.drawer.settings

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import com.example.settingSlidingWindows.SettingScope
import ui.utils.Resources


fun SettingScope.help() {
    item(
        title = Resources.getString("settings/help"),
        subTitle = Resources.getString("settings/help_sub_title"),
        icon = {
            Icon(
                painter = Resources.getIcon("settings/help40"),
                tint = MaterialTheme.colorScheme.inverseOnSurface,
                contentDescription = null
            )
        }
    ) {
        Header(null, null)
    }
}