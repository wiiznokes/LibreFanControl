package ui.configuration.confDialogs

import FState
import ui.settings.ConfInfo
import ui.settings.Settings
import model.item.BaseI.Companion.checkNameValid
import model.item.NameException
import proto.ConfHelper
import proto.SettingsHelper

class ConfVM(
    val settings: Settings = FState.settings,
) {

    fun saveConfiguration(name: String): Boolean {
        val confId = FState.settings.confId.value
        val index = FState.settings.getIndexInfo(confId)

        if (confId == null || index == null) {
            println("save conf: id == null -> return")
            return false
        }

        try {
            checkNameValid(
                names = settings.confInfoList.map { item ->
                    item.name.value
                },
                name = name,
                index = index
            )
        } catch (e: NameException) {
            println("save conf: NameException -> return")
            return false
        }
        println("save conf: id = $confId")
        settings.confInfoList[index].name.value = name
        SettingsHelper.writeSettings()
        ConfHelper.writeConf(confId, name)
        return true
    }

    fun onChangeConfiguration(id: String?) {
        if (id != null) {
            if (!ConfHelper.loadConf(id)) {
                return
            }
        }

        settings.confId.value = id
        SettingsHelper.writeSettings()
    }

    fun addConfiguration(name: String, id: String): Boolean {
        println("addConfiguration: $id, $name")
        try {
            checkNameValid(
                names = settings.confInfoList.map { item ->
                    item.name.value
                },
                name = name
            )
        } catch (e: NameException) {
            println("add conf: NameException -> return false")
            return false
        }

        settings.confId.value = id
        settings.confInfoList.add(ConfInfo(id, name))
        SettingsHelper.writeSettings()
        ConfHelper.writeConf(id, name)

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