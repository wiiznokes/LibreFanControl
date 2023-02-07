package proto

import model.ConfInfo
import model.Languages
import model.Settings
import model.Themes
import proto.generated.setting.*
import java.io.File

private const val SETTING_FILE = "conf/setting"

class SettingsHelper {

    companion object {

        fun checkSetting(): Boolean = getSettingsFile().exists()

        fun loadSetting(): Settings =
            with(getSettingsFile()) { PSetting.parseFrom( readBytes()) }.let {
                Settings(
                    language = when (it.pLanguage) {
                        PLanguages.EN -> Languages.en
                        PLanguages.FR -> Languages.fr
                        else -> {
                            println("error, unknown language")
                            Languages.en
                        }
                    },
                    confId = it.pConfIdOrNull.let { id -> id?.pId },
                    confInfoList = it.pConfInfosList.map { confInfo ->
                        ConfInfo(
                            id = confInfo.pId,
                            name = confInfo.pName
                        )

                    },
                    updateDelay = it.pUpdateDelay,
                    theme = when (it.pTheme) {
                        PThemes.DARK -> Themes.dark
                        PThemes.LIGHT -> Themes.light
                        PThemes.SYSTEM -> Themes.system
                        else -> {
                            println("error, unknown theme")
                            Themes.system
                        }
                    },
                    firstStart = it.pFirstStart,
                    launchAtStartUp = it.pLaunchAtStartUp,
                    degree = it.pDegree

                )
            }


        fun writeSetting(settings: Settings) {
            pSetting {
                pLanguage = when (settings.language.value) {
                    Languages.en -> PLanguages.EN
                    Languages.fr -> PLanguages.FR
                }
                pConfId = nullableId { settings.configId.value }
                settings.confInfoList.forEachIndexed { index, confInfo ->
                    pConfInfos[index] = pConfInfo {
                        pId = confInfo.id
                        pName = confInfo.name.value
                    }
                }
                pUpdateDelay = settings.updateDelay.value
                pTheme = when (settings.theme.value) {
                    Themes.system -> PThemes.SYSTEM
                    Themes.light -> PThemes.LIGHT
                    Themes.dark -> PThemes.DARK
                }
                pFirstStart = settings.firstStart.value
                pLaunchAtStartUp = settings.launchAtStartUp.value
                pDegree = settings.degree.value
            }.let {
                with(getSettingsFile()) {
                    writeBytes(it.toByteArray())
                }
            }
        }


        private fun getSettingsFile(): File = File(System.getProperty("compose.application.resources.dir"))
            .resolve(SETTING_FILE)
    }

}