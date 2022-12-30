package model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class SettingsModel(
    var language: String = "en",
    val configList: SnapshotStateList<ConfigurationModel> = mutableStateListOf(),
    var configId: Long? = null,
    var updateDelay: Int = 2
)
