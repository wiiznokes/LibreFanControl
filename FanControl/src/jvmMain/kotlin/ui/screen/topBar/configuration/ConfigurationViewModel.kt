package ui.screen.topBar.configuration

import State
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.Configuration
import ui.utils.checkNameTaken

class ConfigurationViewModel(
    private val _configList: MutableStateFlow<SnapshotStateList<Configuration>> = State._configList,
    private val _indexConfig: MutableStateFlow<MutableState<Int>> = State._indexConfig
) {

    val configList = _configList.asStateFlow()
    val indexConfig = _indexConfig.asStateFlow().value

    fun saveConfiguration(name: String) {

        try {
            checkNameTaken(
                names = configList.value.map { item ->
                    item.name
                },
                name = name,
                index = indexConfig.value
            )
        } catch (e: Exception) {
            return
        }


        _configList.update {
            it[indexConfig.value] = it[indexConfig.value].copy(
                name = name
            )
            it
        }
    }

    fun onChangeConfiguration(index: Int) {
        _indexConfig.update {
            it.value = index
            it
        }
    }

    fun addConfiguration(name: String, id: Long): Boolean {
        try {
            checkNameTaken(
                names = configList.value.map { item ->
                    item.name
                },
                name = name
            )
        } catch (e: Exception) {
            return false
        }

        _configList.update {
            it.add(
                Configuration(
                    id = id,
                    name = name
                )
            )
            it
        }

        _indexConfig.update {
            it.value = configList.value.lastIndex
            it
        }

        return true
    }

    fun removeConfiguration(index: Int) {
        _configList.update {
            it.removeAt(index)
            it
        }
    }
}