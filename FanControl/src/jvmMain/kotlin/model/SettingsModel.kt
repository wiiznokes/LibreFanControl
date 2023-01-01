package model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class SettingsModel(
    var language: String = "en",
    val configList: SnapshotStateList<ConfigurationModel> = mutableStateListOf(),
    var configId: MutableState<Long?> = mutableStateOf(null),
    var updateDelay: Int = 2
)
