package ui.screen.drawer.secondView

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.settingSlidingWindows.SettingScope
import ui.utils.Resources


fun SettingScope.info() {
    item(
        title = Resources.getString("settings/info"),
        subTitle = Resources.getString("settings/info_sub_title"),
        icon = {
            Icon(
                painter = Resources.getIcon("settings/info40"),
                tint = MaterialTheme.colorScheme.inverseOnSurface,
                contentDescription = null
            )
        }
    ) {
        Header(null, null)
    }

}


@Composable
fun settingInfo() {

}