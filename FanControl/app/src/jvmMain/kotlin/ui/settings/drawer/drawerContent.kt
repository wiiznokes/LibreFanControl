package ui.settings.drawer

import FState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.settingSlidingWindows.Setting
import com.example.settingSlidingWindows.SettingDefaults
import com.example.settingSlidingWindows.rememberSettingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ui.component.managerText
import ui.settings.*
import ui.theme.LocalColors
import utils.Resources

private val viewModel = SettingsVM()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun drawerContent(
    drawerState: DrawerState,
    scope: CoroutineScope,
) {

    launchAtStartUpDialog { viewModel.onLaunchAtStartUpChange(it) }

    val settingState = rememberSettingState(key = drawerState.isOpen)
    if (drawerState.isOpen) {
        FState.ui.editModeActivated.value = false
    }

    val settings = viewModel.settings

    Setting(
        modifier = Modifier
            .width(250.dp)
            .fillMaxHeight(),
        settingState = settingState,
        settingColors = SettingDefaults.settingColors(
            container = LocalColors.current.secondContainer,
            onContainer = LocalColors.current.onSecondContainer,
            background = LocalColors.current.secondBackground,
            onBackground = LocalColors.current.onSecondBackground
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
            language = settings.language
        ) { viewModel.onLanguageChange(it) }
        theme(
            theme = settings.theme
        ) { viewModel.onThemeChange(it) }

        group(text = Resources.getString("settings/trans/lifecycle"))



        launchAtStartUp(
            launchAtStartUp = settings.launchAtStartUp
        ) { viewModel.onLaunchAtStartUpChange(it) }


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
    Column(
        modifier = Modifier
            .background(color = LocalColors.current.secondBackground)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            managerText(
                modifier = Modifier
                    .padding(start = 25.dp, top = 40.dp, bottom = 40.dp),
                text = Resources.getString("title/setting"),
                color = LocalColors.current.onSecondBackground,
                style = MaterialTheme.typography.titleLarge
            )

            IconButton(
                onClick = { scope.launch { drawerState.close() } }
            ) {
                Icon(
                    painter = Resources.getIcon("arrow/arrow_back40"),
                    contentDescription = Resources.getString("ct/close_drawer"),
                    tint = LocalColors.current.onSecondBackground
                )
            }
        }
    }
}