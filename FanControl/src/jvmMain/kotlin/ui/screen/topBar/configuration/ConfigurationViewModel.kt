package ui.screen.topBar.configuration

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.Configuration

class ConfigurationViewModel(
    private val _configList: MutableStateFlow<SnapshotStateList<Configuration>> = State._configList,
    private val _currentConfig: MutableStateFlow<MutableState<Configuration?>> = State._config
) {

    val configList = _configList.asStateFlow()
    val currentConfig = _currentConfig.asStateFlow()

    fun saveConfiguration(id: Long, name: String) {

    }

    fun onChangeName(name: String, id: Long? = null) {

    }

    fun onChangeConfiguration(id: Long) {

    }

    fun addConfiguration(name: String) {

    }

    fun removeConfiguration(id: Long) {

    }
}