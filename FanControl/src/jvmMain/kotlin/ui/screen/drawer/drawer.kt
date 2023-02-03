package ui.screen.drawer

import State
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.settingSlidingWindows.Setting
import com.example.settingSlidingWindows.SettingDefaults
import com.example.settingSlidingWindows.rememberSettingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ui.component.managerText
import ui.screen.drawer.settings.*
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
            managerHeader(
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

        group(text = Resources.getString("settings/trans/lifecycle"))

        exitOnClose(
            exitOnClose = settings.exitOnClose,
            onExitChange = { viewModel.onExitOnCloseChange(it) }
        )
        /*
        launchAtStartUp(
            launchAtStartUp = settings.launchAtStartUp,
            onLaunchAtStartUpChange = { viewModel.onLaunchAtStartUpChange(it) }
        )
        */

        group(text = Resources.getString("settings/trans/donate"))
        donate()

        group(text = Resources.getString("settings/trans/other"))
        info()
        help()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun managerHeader(
    drawerState: DrawerState,
    scope: CoroutineScope,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            managerText(
                modifier = Modifier
                    .padding(start = 25.dp, top = 40.dp, bottom = 50.dp),
                text = Resources.getString("title/setting"),
                color = MaterialTheme.colorScheme.inverseOnSurface,
                style = MaterialTheme.typography.titleLarge
            )

            IconButton(
                onClick = { scope.launch { drawerState.close() } }
            ) {
                Icon(
                    painter = Resources.getIcon("arrow/arrow_back48"),
                    contentDescription = Resources.getString("ct/close_drawer"),
                    tint = MaterialTheme.colorScheme.inverseOnSurface
                )
            }
        }
    }
}