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
import ui.screen.drawer.firstView.headerSetting
import ui.screen.drawer.secondView.*
import ui.utils.Resources

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
        updateDelay(
            onDelayChange = { viewModel.onUpdateDelay(it) },
            updateDelay = settings.updateDelay
        )
        language(
            language = settings.language,
            onLanguageChange = { viewModel.onLanguageChange(it) }
        )
        theme(
            theme = settings.theme,
            onThemeChange = { viewModel.onThemeChange(it) }
        )

        group(text = Resources.getString("settings/trans/donate"))
        donate()

        group(text = Resources.getString("settings/trans/other"))
        info()
        help()
    }
}