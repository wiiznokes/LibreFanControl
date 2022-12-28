package ui.screen.topBar.configuration

import State
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import configuration.Configuration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.ConfigurationModel
import model.item.ControlItem
import model.item.SensorItem
import model.item.behavior.BehaviorItem
import org.json.JSONObject
import settings.Settings
import utils.checkNameTaken

class ConfigurationViewModel(
    private val _configList: MutableStateFlow<SnapshotStateList<ConfigurationModel>> = State._configList,
    private val _idConfig: MutableStateFlow<MutableState<Long?>> = State._idConfig,

    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList,
    private val _fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._fanItemList,
    private val _tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._tempItemList
) {

    val controlItemList = _controlItemList.asStateFlow()
    val behaviorItemList = _behaviorItemList.asStateFlow()
    val fanItemList = _fanItemList.asStateFlow()
    val tempItemList = _tempItemList.asStateFlow()

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

        Configuration.saveConfig(
            configuration = configList.value[index],
            controlItemList = controlItemList.value,
            behaviorItemList = behaviorItemList.value,
            fanItemList = fanItemList.value,
            tempItemList = tempItemList.value
        )
    }

    fun onChangeConfiguration(id: Long?) {

        when (id) {
            null -> Settings.setSetting("config", JSONObject.NULL)
            else -> {
                Settings.setSetting("config", id)
                Configuration.loadConfig(
                    configId = id,
                    controlItemList = State._controlItemList,
                    behaviorItemList = State._behaviorItemList,
                    fanItemList = State._fanItemList,
                    tempItemList = State._tempItemList,

                    fanList = State._fanList.value,
                    tempList = State._tempList.value
                )
            }
        }

        _idConfig.update {
            it.value = when (id) {
                null -> null
                else -> id
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

        val newConfig = ConfigurationModel(
            id = id,
            name = name
        )

        _configList.update {
            it.add(newConfig)
            it
        }

        _idConfig.update {
            it.value = id
            it
        }

        Configuration.saveConfig(
            configuration = newConfig,
            controlItemList = controlItemList.value,
            behaviorItemList = behaviorItemList.value,
            fanItemList = fanItemList.value,
            tempItemList = tempItemList.value
        )

        Settings.setSetting(
            path = "config",
            value = id
        )
        Settings.setSetting(
            path = "configList/$id",
            value = name
        )

        return true
    }

    fun removeConfiguration(id: Long, index: Int) {

        val removeCurrent = id == idConfig.value

        if (removeCurrent)
            Settings.setSetting("config", JSONObject.NULL)

        Settings.removeConfig(id)


        if (removeCurrent) {
            _idConfig.update {
                it.value = null
                it
            }
        }
        _configList.update {
            it.removeAt(index)
            it
        }
        Configuration.deleteConfig(id)
    }
}