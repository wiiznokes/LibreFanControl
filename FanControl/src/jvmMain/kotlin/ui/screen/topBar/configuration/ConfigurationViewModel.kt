package ui.screen.topBar.configuration

import State
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import configuration.Configuration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.ConfigurationModel
import model.hardware.Sensor
import model.item.ControlItem
import model.item.SensorItem
import model.item.behavior.BehaviorItem
import org.json.JSONObject
import settings.Settings
import utils.NameException
import utils.checkNameTaken

class ConfigurationViewModel(
    private val _configList: MutableStateFlow<SnapshotStateList<ConfigurationModel>> = State._configList,
    private val _idConfig: MutableStateFlow<MutableState<Long?>> = State._idConfig,

    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList,
    private val _fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._fanItemList,
    private val _tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._tempItemList,

    private val _fanList: MutableStateFlow<SnapshotStateList<Sensor>> = State._fanList,
    private val _tempList: MutableStateFlow<SnapshotStateList<Sensor>> = State._tempList
) {
    val configList = _configList.asStateFlow()
    val idConfig = _idConfig.asStateFlow().value

    private val controlItemList = _controlItemList.asStateFlow()
    private val behaviorItemList = _behaviorItemList.asStateFlow()
    private val fanItemList = _fanItemList.asStateFlow()
    private val tempItemList = _tempItemList.asStateFlow()

    private val fanList = _fanList.asStateFlow()
    private val tempList = _tempList.asStateFlow()


    // save conf is only visible when idConfig != null
    fun saveConfiguration(name: String, index: Int, id: Long) {

        try {
            checkNameTaken(
                names = configList.value.map { item ->
                    item.name
                },
                name = name,
                index = index
            )
        } catch (e: NameException) {
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
            tempItemList = tempItemList.value,
            fanList = fanList.value,
            tempList = tempList.value
        )
        Settings.setSetting(
            path = "configList/$id",
            value = name
        )
    }

    fun onChangeConfiguration(id: Long?) {

        _idConfig.update {
            it.value = id
            it
        }

        when (id) {
            null -> Settings.setSetting("config", JSONObject.NULL)
            else -> {
                Configuration.loadConfig(
                    configId = id,
                    controlItemList = _controlItemList,
                    behaviorItemList = _behaviorItemList,
                    fanItemList = _fanItemList,
                    tempItemList = _tempItemList,

                    fanList = _fanList,
                    tempList = _tempList
                )
                Settings.setSetting("config", id)
            }
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
            tempItemList = tempItemList.value,
            fanList = fanList.value,
            tempList = tempList.value
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

        _configList.update {
            it.removeAt(index)
            it
        }

        // check if current config has been removed
        if (id == idConfig.value) {
            _idConfig.update {
                it.value = null
                it
            }
            Settings.setSetting("config", JSONObject.NULL)
        }

        Configuration.deleteConfig(id)
        Settings.removeConfig(id)
    }
}