package ui.screen.drawer

import State
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.settingSlidingWindows.Setting
import com.example.settingSlidingWindows.SettingDefaults
import com.example.settingSlidingWindows.rememberSettingState
import kotlinx.coroutines.CoroutineScope
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
            icon = {
                Icon(
                    painter = getIcon("settings/history40"),
                    contentDescription = null
                )
            }
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
            icon = {
                Icon(
                    painter = getIcon("settings/translate40"),
                    contentDescription = null
                )
            }
        ) {
            Header(null, null)
            settingLanguage(
                onLanguageChange = { viewModel.onLanguageChange(it) }
            )
        }

        item(
            title = Resources.getString("settings/theme"),
            subTitle = Resources.getString("theme/${settings.theme}"),
            icon = {
                Icon(
                    painter = getIcon("settings/dark_mode40"),
                    contentDescription = null
                )
            }
        ) {
            Header(null, null)
            settingTheme(
                onThemeChange = { viewModel.onThemeChange(it) }
            )
        }

        item(
            title = Resources.getString("settings/donate"),
            icon = {
                Icon(
                    painter = getIcon("settings/attach_money40"),
                    contentDescription = null
                )
            }
        ) {
            Header(null, null)
            settingDonate()
        }

        item(
            title = Resources.getString("settings/info"),
            subTitle = Resources.getString("settings/info_sub_title"),
            icon = {
                Icon(
                    painter = getIcon("settings/info40"),
                    contentDescription = null
                )
            }
        ) {
            Header(null, null)
            settingInfo()
        }

        item(
            title = Resources.getString("settings/help"),
            subTitle = Resources.getString("settings/help_sub_title"),
            icon = {
                Icon(
                    painter = getIcon("settings/help40"),
                    contentDescription = null
                )
            }
        ) {
            Header(null, null)
            settingHelp()
        }


    }
}
