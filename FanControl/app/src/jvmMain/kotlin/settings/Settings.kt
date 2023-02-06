package settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import configuration.ConfigurationModel
import ui.screen.drawer.settings.Languages
import ui.screen.drawer.settings.Themes

class Settings(
    val language: MutableState<Languages> = mutableStateOf(Languages.en),
    val configList: SnapshotStateList<ConfigurationModel> = mutableStateListOf(),
    val configId: MutableState<String?> = mutableStateOf(null),
    val updateDelay: MutableState<Int> = mutableStateOf(2),
    val theme: MutableState<Themes> = mutableStateOf(Themes.system),
    var firstStart: Boolean = true,
    var launchAtStartUp: Boolean = false,
    var degree: Boolean = true,
    var exitOnCloseSet: Boolean = false,
    var exitOnClose: Boolean = true,
)
