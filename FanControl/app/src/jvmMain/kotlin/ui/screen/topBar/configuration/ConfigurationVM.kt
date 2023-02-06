package ui.screen.topBar.configuration

import State
import configuration.Configuration
import configuration.ConfigurationModel
import external.ExternalManager
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONObject
import settings.SettingsHelper
import settings.Settings
import utils.Name.Companion.checkNameTaken
import utils.NameException

class ConfigurationVM(
    val settings: MutableStateFlow<Settings> = State.settings
) {

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
        settings.value = settings.value.copy(
            configList = settings.value.configList.apply {
                this[index] = this[index].copy(
                    name = name
                )
            }
        )

        Configuration.saveConfig(
            configuration = settings.value.configList[index]
        )
        SettingsHelper.setSetting(
            path = "config_list/$id",
            value = name
        )

        ExternalManager.reloadConfig(id)
    }

    fun onChangeConfiguration(id: Long?) {

        when (id) {
            null -> SettingsHelper.setSetting("config_id", JSONObject.NULL)
            else -> {
                SettingsHelper.setSetting("config_id", id)
                Configuration.loadConfig(id)
            }
        }
        settings.value = settings.value.copy(
            configId = id
        )
        ExternalManager.reloadConfig(id)
    }

    fun addConfiguration(name: String, id: Long): Boolean {
        try {
            checkNameTaken(
                names = settings.value.configList.map { item ->
                    item.name
                },
                name = name
            )
        } catch (e: NameException) {
            return false
        }

        val newConfig = ConfigurationModel(id, name)

        settings.value.configList.add(newConfig)
        settings.value = settings.value.copy(
            configId = id
        )


        Configuration.saveConfig(newConfig)

        SettingsHelper.setSetting(
            path = "config_id",
            value = id
        )
        SettingsHelper.setSetting(
            path = "config_list/$id",
            value = name
        )

        ExternalManager.reloadConfig(id)
        return true
    }

    fun removeConfiguration(id: Long, index: Int) {
        settings.value.configList.removeAt(index)

        Configuration.deleteConfig(id)
        SettingsHelper.removeConfig(id)

        // check if current config has been removed
        if (id == settings.value.configId) {
            settings.value = settings.value.copy(
                configId = null
            )
            SettingsHelper.setSetting("config_id", JSONObject.NULL)
            ExternalManager.reloadConfig(id)
        }
    }
}