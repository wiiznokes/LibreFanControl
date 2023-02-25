package ui.configuration.confDialogs

import FState
import androidx.compose.runtime.MutableState
import model.ConfInfo
import model.Settings
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
        SettingsHelper.writeSettings(false)
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

    fun addConfiguration(name: MutableState<String>, id: String): Boolean {
        val actualName = name.value
        println("addConfiguration: $id, $actualName")
        try {
            checkNameValid(
                names = settings.confInfoList.map { item ->
                    item.name.value
                },
                name = actualName
            )
        } catch (e: NameException) {
            println("add conf: NameException -> return false")
            return false
        }
        println("add conf: id = $id")

        settings.confId.value = id
        settings.confInfoList.add(ConfInfo(id, actualName))
        SettingsHelper.writeSettings(false)
        ConfHelper.writeConf(id, actualName)

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