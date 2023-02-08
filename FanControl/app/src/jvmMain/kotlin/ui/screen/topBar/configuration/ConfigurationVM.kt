package ui.screen.topBar.configuration

import State
import model.ConfInfo
import model.Settings
import model.item.BaseI.Companion.checkNameTaken
import model.item.NameException
import proto.ConfHelper
import proto.SettingsHelper

class ConfigurationVM(
    val settings: Settings = State.settings,
) {

    fun saveConfiguration(name: String, index: Int, id: String?) {
        if (id == null) return

        try {
            checkNameTaken(
                names = settings.confInfoList.map { item ->
                    item.name.value
                },
                name = name,
                index = index
            )
        } catch (e: NameException) {
            return
        }

        settings.confInfoList[index].name.value = name
        SettingsHelper.writeSettings()
        ConfHelper.writeConf(id)

    }

    fun onChangeConfiguration(id: String?) {
        settings.confId.value = id
        if (id != null)
            ConfHelper.loadConf(id)
    }

    fun addConfiguration(name: String, id: String): Boolean {
        try {
            checkNameTaken(
                names = settings.confInfoList.map { item ->
                    item.name.value
                },
                name = name
            )
        } catch (e: NameException) {
            return false
        }

        settings.confId.value = id
        settings.confInfoList.add(ConfInfo(id, name))
        SettingsHelper.writeSettings()

        ConfHelper.writeConf(id)

        return true
    }

    fun removeConfiguration(id: String, index: Int) {
        settings.confInfoList.removeAt(index)
        if (settings.confId.value == id) {
            settings.confId.value = null
        }
        SettingsHelper.writeSettings()
    }
}