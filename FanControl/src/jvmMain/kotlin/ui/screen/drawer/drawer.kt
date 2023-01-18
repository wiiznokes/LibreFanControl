package ui.screen.drawer

import State
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import com.example.settingSlidingWindows.Setting
import com.example.settingSlidingWindows.SettingColors
import com.example.settingSlidingWindows.SettingDefaults
import com.example.settingSlidingWindows.rememberSettingState
import kotlinx.coroutines.CoroutineScope
import ui.screen.drawer.firstView.headerSetting
import ui.screen.drawer.secondView.*
import ui.utils.Resources
import ui.utils.Resources.Companion.getIcon

private val viewModel = DrawerVM()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun drawer(
    drawerState: DrawerState,
    scope: CoroutineScope
) {

    val settingState = rememberSettingState(key = drawerState.isOpen)
    val settings = State.settings.collectAsState().value

    Setting(
        settingState = settingState,
        settingColors = SettingDefaults.settingColors(
            container = MaterialTheme.colorScheme.inverseSurface,
            onContainer = MaterialTheme.colorScheme.inverseOnSurface
        )
    ) {
        header {
            headerSetting(
                drawerState = drawerState,
                scope = scope
            )
        }

        item(
            title = Resources.getString("settings/update_delay"),
            subTitle = settings.updateDelay.toString(),
            icon = { managerIcon("settings/history40") }
        ) {
            Header(null, null)
            settingTimeUpdate(
                onDelayChange = { viewModel.onUpdateDelay(it) },
                updateDelay = settings.updateDelay
            )
        }

        item(
            title = Resources.getString("settings/language"),
            subTitle = Resources.getString("language/${settings.language}"),
            icon = { managerIcon("settings/translate40") }
        ) {
            Header(null, null)
            settingLanguage(onLanguageChange = { viewModel.onLanguageChange(it) })
        }

        item(
            title = Resources.getString("settings/theme"),
            subTitle = Resources.getString("theme/${settings.theme}"),
            icon = { managerIcon("settings/dark_mode40") }
        ) {
            Header(null, null)
            settingTheme(onThemeChange = { viewModel.onThemeChange(it) })
        }

        group(text = Resources.getString("settings/trans/donate"))

        item(
            settingColors = SettingColors(
                container = Color.Yellow,
                onContainer = Color.Black
            ),
            title = Resources.getString("settings/donate"),
            icon = { managerIcon("settings/attach_money40", Color.Black) }
        ) {
            Header(null, null)
            settingDonate()
        }

        group(text = Resources.getString("settings/trans/other"))

        item(
            title = Resources.getString("settings/info"),
            subTitle = Resources.getString("settings/info_sub_title"),
            icon = { managerIcon("settings/info40") }
        ) {
            Header(null, null)
            settingInfo()
        }

        item(
            title = Resources.getString("settings/help"),
            subTitle = Resources.getString("settings/help_sub_title"),
            icon = { managerIcon("settings/help40") }
        ) {
            Header(null, null)
            settingHelp()
        }


    }
}


@Composable
private fun managerIcon(
    iconPath: String,
    color: Color = MaterialTheme.colorScheme.inverseOnSurface,
    contentDescription: String? = null
) {
    Icon(
        painter = getIcon(iconPath),
        tint = color,
        contentDescription = contentDescription
    )
}