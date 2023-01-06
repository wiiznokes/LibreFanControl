package settings

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import configuration.ConfigurationModel
import ui.screen.drawer.secondView.Languages
import ui.screen.drawer.secondView.Themes

data class SettingsModel(
    var language: Languages = Languages.en,
    var theme: Themes = Themes.system,
    val configList: SnapshotStateList<ConfigurationModel> = mutableStateListOf(),
    var configId: Long? = null,
    var updateDelay: Int = 2
)
