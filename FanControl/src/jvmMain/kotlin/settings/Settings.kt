package settings

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import configuration.ConfigurationModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject
import org.json.JSONTokener
import ui.screen.drawer.secondView.Languages
import ui.screen.drawer.secondView.Themes
import utils.getJsonValue
import utils.setJsonValue
import java.io.File


const val DIR_CONF = "conf/"

const val SETTINGS_FILE_NAME = "settings.json"
private const val SETTINGS_SOT_FILE_NAME = "settings.sot.json"


/**
 * write and retrieve setting via settings.json file
 *
 * the first instance will check if settings.json exist
 * if not, it will create it with settings.sot.json
 * else, setting state will be set with it
 */
class Settings {
    companion object {
        private val confFolder = File(System.getProperty("compose.application.resources.dir"))
            .resolve(DIR_CONF)

        private var rootObj: JSONObject
        private val file: File = confFolder.resolve(SETTINGS_FILE_NAME)

        /**
         * initialize the settings.json file using the settings.sot.json file
         * which serves as a source of truth, to avoid committing a modified settings.json file.
         * This avoids using "smudge", the git tool, because it is really not practical.
         */
        init {
            if (!file.exists()) {
                val settingsSotFile = confFolder.resolve(SETTINGS_SOT_FILE_NAME)
                file.writeText(settingsSotFile.readText())
            }

            val string = file.bufferedReader().readText()
            rootObj = JSONTokener(string).nextValue() as JSONObject
            initSettingsState(State.settings)
        }

        fun setSetting(path: String, value: Any?) {
            updateVariable(
                setJsonValue(
                    path = path,
                    value = value,
                    obj = rootObj
                )
            )
        }

        /**
         * remove config from configList
         */
        fun removeConfig(id: Long) {
            val path = "configList/$id"
            updateVariable(
                setJsonValue(
                    path = path,
                    value = null,
                    obj = rootObj
                )
            )
        }


        private fun initSettingsState(
            settings: MutableStateFlow<SettingsModel>
        ) {
            settings.update {
                it.language = Languages from getSetting("language")!!
                it.configId = getSetting("configId")
                getConfigList(it.configList)
                it.updateDelay = getSetting("updateDelay")!!
                it.theme = Themes from getSetting("theme")!!
                it
            }
        }

        private fun getConfigList(configList: SnapshotStateList<ConfigurationModel>) {
            val jsonList = rootObj.getJSONObject("configList")

            for (key in jsonList.keys()) {
                configList.add(
                    ConfigurationModel(
                        id = key.toLong(),
                        name = jsonList.getString(key)
                    )
                )
            }
        }

        private fun <T> getSetting(path: String): T? {
            return getJsonValue(
                path = path,
                obj = rootObj
            )
        }


        private fun updateVariable(newObj: JSONObject) {
            file.writeText(
                newObj.toString()
            )
            rootObj = newObj
        }
    }
}