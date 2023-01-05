package ui.screen.drawer

import State
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import ui.screen.drawer.firstView.settingFistView
import ui.screen.drawer.secondView.*

private val viewModel = DrawerVM()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun drawer(
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    val settingType = remember(drawerState.isOpen) {
        mutableStateOf(SettingType.FIRST_VIEW)
    }

    val lazyListState = remember(drawerState.isOpen) {
        LazyListState()
    }

    val settings = State.settings.collectAsState().value

    when (settingType.value) {
        SettingType.FIRST_VIEW -> settingFistView(
            drawerState = drawerState,
            scope = scope,
            settingType = settingType,
            lazyListState = lazyListState,
            settings = settings
        )

        SettingType.TIME_UPDATE -> settingTimeUpdate(
            settingState = settingType,
            onDelayChange = { viewModel.onUpdateDelay(it) },
            updateDelay = settings.updateDelay
        )

        SettingType.LANGUAGE -> settingLanguage(
            settingState = settingType,
            onLanguageChange = { viewModel.onLanguageChange(it) }
        )

        SettingType.THEME -> settingTheme(
            settingState = settingType,
            onThemeChange = { viewModel.onThemeChange(it) }
        )

        SettingType.DONATE -> settingDonate(settingType)
        SettingType.INFO -> settingInfo(settingType)
        SettingType.HELP -> settingHelp(settingType)
    }

}

enum class SettingType {
    FIRST_VIEW,
    TIME_UPDATE,
    LANGUAGE,
    THEME,
    DONATE,
    INFO,
    HELP
}