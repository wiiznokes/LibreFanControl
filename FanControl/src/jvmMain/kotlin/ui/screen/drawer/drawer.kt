package ui.screen.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import ui.screen.drawer.firstView.settingFistView
import ui.screen.drawer.secondView.*
import kotlin.TODO

private val viewModel = DrawerVM()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun drawer(
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    val settingState = remember(
        drawerState.isOpen
    ) {
        mutableStateOf(SettingType.FIRST_VIEW)
    }

    when (settingState.value) {
        SettingType.FIRST_VIEW -> settingFistView(
            drawerState = drawerState,
            scope = scope,
            settingState = settingState
        )

        SettingType.TIME_UPDATE -> settingTimeUpdate(settingState)
        SettingType.LANGUAGE -> settingLanguage(settingState)
        SettingType.THEME -> settingTheme(settingState)
        SettingType.DONATE -> settingDonate(settingState)
        SettingType.INFO -> settingInfo(settingState)
        SettingType.HELP -> settingHelp(settingState)
    }

}