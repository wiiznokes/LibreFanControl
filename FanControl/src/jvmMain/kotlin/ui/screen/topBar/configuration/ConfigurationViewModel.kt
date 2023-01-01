package ui.screen.topBar.configuration

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import configuration.Configuration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.ConfigurationModel
import model.SettingsModel
import model.hardware.Sensor
import model.item.ControlItem
import model.item.SensorItem
import model.item.behavior.BehaviorItem
import org.json.JSONObject
import settings.Settings
import utils.NameException
import utils.checkNameTaken

class ConfigurationViewModel(
    private val _settings: MutableStateFlow<SettingsModel> = State._settings,
    val controlChange: StateFlow<Boolean> = State._controlsChange.asStateFlow(),

    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList,
    private val _fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._fanItemList,
    private val _tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._tempItemList,

    private val _fanList: MutableStateFlow<SnapshotStateList<Sensor>> = State._fanList,
    private val _tempList: MutableStateFlow<SnapshotStateList<Sensor>> = State._tempList,
    private val _controlsChange: MutableStateFlow<Boolean> = State._controlsChange
) {
    val settings = _settings.asStateFlow()

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
                names = settings.value.configList.map { item ->
                    item.name
                },
                name = name,
                index = index
            )
        } catch (e: NameException) {
            return
        }

        _settings.update {
            it.configList[index].name = name
            it
        }

        Configuration.saveConfig(
            configuration = settings.value.configList[index],
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

        _settings.update {
            it.configId.value = id
            it
        }

        when (id) {
            null -> Settings.setSetting("configId", JSONObject.NULL)
            else -> {
                if (controlChange.value)
                    return
                _controlsChange.value = true
                Configuration.loadConfig(
                    configId = id,
                    controlItemList = _controlItemList,
                    behaviorItemList = _behaviorItemList,
                    fanItemList = _fanItemList,
                    tempItemList = _tempItemList,

                    fanList = _fanList,
                    tempList = _tempList
                )
                Settings.setSetting("configId", id)
            }
        }
    }

    fun addConfiguration(name: String, id: Long): Boolean {
        try {
            checkNameTaken(
                names = settings.value.configList.map { item ->
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

        _settings.update {
            it.configList.add(newConfig)
            it.configId.value = id
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
            path = "configId",
            value = id
        )
        Settings.setSetting(
            path = "configList/$id",
            value = name
        )
        return true
    }

    fun removeConfiguration(id: Long, index: Int) {

        _settings.update {
            it.configList.removeAt(index)
            it
        }

        // check if current config has been removed
        if (id == settings.value.configId.value) {
            _settings.update {
                it.configId.value = null
                it
            }
            Settings.setSetting("configId", JSONObject.NULL)
        }

        Configuration.deleteConfig(id)
        Settings.removeConfig(id)
    }
}