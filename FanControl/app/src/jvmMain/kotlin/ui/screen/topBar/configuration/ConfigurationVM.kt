package ui.screen.topBar.configuration

import State
import model.item.BaseI
import model.item.BaseI.Companion.checkNameTaken
import model.Settings

class ConfigurationVM(
    val settings: Settings = State.settings,
) {

    fun saveConfiguration(name: String, index: Int, id: String) {
        try {
            checkNameTaken(
                names = settings.confInfoList.map { item ->
                    item.name
                },
                name = name,
                index = index
            )
        } catch (e: BaseI.Companion.NameException) {
            return
        }

        settings.confInfoList[index] = settings.confInfoList[index].copy(
            name = name
        )

    }

    fun onChangeConfiguration(id: String?) {


    }

    fun addConfiguration(name: String, id: String): Boolean {
        try {
            checkNameTaken(
                names = settings.confInfoList.map { item ->
                    item.name
                },
                name = name
            )
        } catch (e: BaseI.Companion.NameException) {
            return false
        }

        //val newConfig = ConfigurationModel(id, name)


        return true
    }

    fun removeConfiguration(id: String, index: Int) {
        settings.confInfoList.removeAt(index)
    }
}