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
        if (id == null) {
            println("save conf: id == null -> return")
            return
        }

        try {
            checkNameTaken(
                names = settings.confInfoList.map { item ->
                    item.name.value
                },
                name = name,
                index = index
            )
        } catch (e: NameException) {
            println("save conf: NameException -> return")
            return
        }
        println("save conf: id = $id")
        settings.confInfoList[index].name.value = name
        SettingsHelper.writeSettings(false)
        ConfHelper.writeConf(id)
    }

    fun onChangeConfiguration(id: String?) {
        if (id != null)
            ConfHelper.loadConf(id)

        settings.confId.value = id
        SettingsHelper.writeSettings()
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
            println("add conf: NameException -> return false")
            return false
        }
        println("add conf: id = $id")

        settings.confId.value = id
        settings.confInfoList.add(ConfInfo(id, name))
        SettingsHelper.writeSettings(false)
        ConfHelper.writeConf(id)

        return true
    }

    fun removeConfiguration(id: String, index: Int) {
        println("remove conf: id = $id")

        ConfHelper.removeConf(id)

        settings.confInfoList.removeAt(index)
        if (settings.confId.value == id) {
            settings.confId.value = null
            SettingsHelper.writeSettings()
        }
    }
}