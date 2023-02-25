package ui.drawer.settings

import androidx.compose.material3.Icon
import com.example.settingSlidingWindows.SettingScope
import ui.theme.LocalColors
import utils.Resources


fun SettingScope.info() {
    item(
        title = Resources.getString("settings/info"),
        subTitle = Resources.getString("settings/info_sub_title"),
        icon = {
            Icon(
                painter = Resources.getIcon("settings/info24"),
                tint = LocalColors.current.onSecondContainer,
                contentDescription = null
            )
        },
        showTopLine = true
    ) {
        header(null, null)
    }
}