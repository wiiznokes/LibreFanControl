package ui.screen.drawer.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.settingSlidingWindows.SettingScope
import ui.component.managerText
import ui.utils.Resources


/**
 * name need to respect the format in
 * json string file and configuration
 */
@Suppress("EnumEntryName")
enum class Themes {
    system,
    light,
    dark;

    companion object {
        infix fun from(value: String): Themes = Themes.values().first { it.name == value }
    }
}


fun SettingScope.theme(
    theme: Themes,
    onThemeChange: (Themes) -> Unit
) {
    item(
        title = Resources.getString("settings/theme"),
        subTitle = Resources.getString("theme/${theme}"),
        icon = {
            Icon(
                painter = Resources.getIcon("settings/dark_mode40"),
                tint = MaterialTheme.colorScheme.inverseOnSurface,
                contentDescription = null
            )
        }
    ) {
        Header(null, null)

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.inverseOnSurface,
            thickness = 2.dp
        )
        LazyColumn {
            items(Themes.values()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onThemeChange(it) }
                ) {
                    managerText(
                        text = Resources.getString("theme/$it"),
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    thickness = 2.dp
                )
            }

            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }
}