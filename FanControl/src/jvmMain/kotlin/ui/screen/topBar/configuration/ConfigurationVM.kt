package ui.screen.topBar.configuration

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import configuration.Configuration
import configuration.ConfigurationModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import org.json.JSONObject
import settings.Settings
import settings.SettingsModel
import utils.Name.Companion.checkNameTaken
import utils.NameException

class ConfigurationVM(
    val settings: MutableStateFlow<SettingsModel> = State.settings,
    val controlChangeList: SnapshotStateList<Boolean> = State.controlChangeList,
    val mutex: Mutex = State.controlChangeMutex
) {

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
        Settings.setSetting(
            path = "configList/$id",
            value = name
        )
    }

    fun onChangeConfiguration(id: Long?) {
        when (id) {
            null -> Settings.setSetting("configId", JSONObject.NULL)
            else -> {
                if (!mutex.tryLock())
                    return

                if (controlChangeList.contains(true)) {
                    mutex.unlock()
                    return
                }

                for (i in controlChangeList.indices) {
                    controlChangeList[i] = true
                }
                Configuration.loadConfig(id)
                for (i in controlChangeList.indices) {
                    controlChangeList[i] = false
                }
                Settings.setSetting("configId", id)
                mutex.unlock()
            }
        }
        settings.value = settings.value.copy(
            configId = id
        )
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

        val newConfig = ConfigurationModel(id, name)

        settings.value.configList.add(newConfig)
        settings.value = settings.value.copy(
            configId = id
        )


        Configuration.saveConfig(newConfig)

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
        settings.value.configList.removeAt(index)

        // check if current config has been removed
        if (id == settings.value.configId) {
            settings.value = settings.value.copy(
                configId = null
            )
            Settings.setSetting("configId", JSONObject.NULL)
        }

        Configuration.deleteConfig(id)
        Settings.removeConfig(id)
    }
}