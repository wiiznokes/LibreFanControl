package model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class SettingsModel(
    val language: MutableState<String> = mutableStateOf("en"),
    val configList: SnapshotStateList<ConfigurationModel> = mutableStateListOf(),
    val configId: MutableState<Long?> = mutableStateOf(null),
    val updateDelay: MutableState<Int> = mutableStateOf(2)
)
