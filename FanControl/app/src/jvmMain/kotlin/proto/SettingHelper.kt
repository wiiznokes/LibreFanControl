package proto

import androidx.compose.runtime.toMutableStateList
import model.ConfInfo
import model.Languages
import model.Settings
import model.Themes
import proto.generated.setting.*
import java.io.File

private const val SETTING_DIR = "setting/setting"

class SettingHelper {

    companion object {
        fun checkSetting(): Boolean = getFile().exists()

        fun getDefault(): Settings = Settings()


        fun loadSetting(): Settings {


            val pSetting = with(getFile()) {
                PSetting.parseFrom(readBytes())
            }

            return Settings(
                language = when (pSetting.pLanguage) {
                    PLanguages.EN -> Languages.en
                    PLanguages.FR -> Languages.fr
                    else -> {
                        println("error, unknown language")
                        Languages.en
                    }
                },
                confId = pSetting.pConfigIdOrNull.let { it?.pId },
                confInfoList = pSetting.pConfInfosList.map {
                    ConfInfo(
                        id = it.pId,
                        name = it.pName
                    )
                }.toMutableStateList(),
                updateDelay = pSetting.pUpdateDelay,
                theme = when(pSetting.pTheme) {
                    PThemes.DARK -> Themes.dark
                    PThemes.LIGHT -> Themes.light
                    PThemes.SYSTEM -> Themes.system
                    else -> {
                        println("error, unknown theme")
                        Themes.system
                    }
                },
                firstStart = pSetting.pFirstStart,
                launchAtStartUp = pSetting.pLaunchAtStartUp,
                degree = pSetting.pDegree
            )
        }

        fun writeSetting(setting: Settings) {
            val pSetting = pSetting {
                pLanguage = when (setting.language.value) {
                    Languages.en -> PLanguages.EN
                    Languages.fr -> PLanguages.FR
                }
                pConfigId = nullableId { setting.configId.value }
                setting.confInfoList.forEachIndexed { index, confInfo ->
                    pConfInfos[index] = pConfInfo {
                        pId = confInfo.id
                        pName = confInfo.name.value
                    }
                }
                pUpdateDelay = setting.updateDelay.value
                pTheme = when(setting.theme.value) {
                    Themes.system -> PThemes.SYSTEM
                    Themes.light -> PThemes.LIGHT
                    Themes.dark -> PThemes.DARK
                }
                pFirstStart = setting.firstStart.value
                pLaunchAtStartUp = setting.launchAtStartUp.value
                pDegree = setting.degree.value
            }


            with(getFile()) {
                writeBytes(pSetting.toByteArray())
            }
        }




        private fun getFile(): File {
            val includeFolder = File(System.getProperty("compose.application.resources.dir"))
            return includeFolder.resolve(SETTING_DIR)
        }
    }

}