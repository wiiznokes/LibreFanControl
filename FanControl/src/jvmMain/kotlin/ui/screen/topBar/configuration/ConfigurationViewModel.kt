package ui.screen.topBar.configuration

import State
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.ConfigurationModel
import utils.checkNameTaken

class ConfigurationViewModel(
    private val _configList: MutableStateFlow<SnapshotStateList<ConfigurationModel>> = State._configList,
    private val _idConfig: MutableStateFlow<MutableState<Long?>> = State._idConfig
) {

    val configList = _configList.asStateFlow()
    val idConfig = _idConfig.asStateFlow().value

    // save conf is only visible when idConfig != null
    fun saveConfiguration(name: String) {
        val index = configList.value.indexOfFirst {
            it.id == idConfig.value
        }

        try {
            checkNameTaken(
                names = configList.value.map { item ->
                    item.name
                },
                name = name,
                index = index

            )
        } catch (e: Exception) {
            return
        }

        _configList.update {
            it[index] = it[index].copy(
                name = name
            )
            it
        }
    }

    fun onChangeConfiguration(index: Int?) {
        _idConfig.update {
            it.value = when (index) {
                null -> null
                else -> configList.value[index].id
            }
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
                ConfigurationModel(
                    id = id,
                    name = name
                )
            )
            it
        }

        _idConfig.update {
            it.value = id
            it
        }

        return true
    }

    fun removeConfiguration(index: Int) {
        if (configList.value[index].id == idConfig.value) {
            _idConfig.update {
                it.value = null
                it
            }
        }
        _configList.update {
            it.removeAt(index)
            it
        }
    }
}