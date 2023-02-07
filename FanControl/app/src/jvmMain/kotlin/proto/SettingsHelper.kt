package proto

import State
import model.ConfInfo
import model.Languages
import model.Themes
import proto.generated.setting.*
import java.io.File

private const val SETTING_FILE = "conf/setting"

class SettingsHelper {

    companion object {
        private val setting = State.settings

        fun checkSetting(): Boolean = getFile().exists()

        fun loadSetting() {
            val pSetting = with(getFile()) {
                PSetting.parseFrom(readBytes())
            }

            setting.language.value = when (pSetting.pLanguage) {
                PLanguages.EN -> Languages.en
                PLanguages.FR -> Languages.fr
                else -> {
                    println("error, unknown language")
                    Languages.en
                }
            }
            setting.configId.value = pSetting.pConfigIdOrNull.let { it?.pId }
            setting.confInfoList.clear()
            pSetting.pConfInfosList.forEach {
                setting.confInfoList.add(
                    ConfInfo(
                        id = it.pId,
                        name = it.pName
                    )
                )
            }
            setting.updateDelay.value = pSetting.pUpdateDelay
            setting.theme.value = when (pSetting.pTheme) {
                PThemes.DARK -> Themes.dark
                PThemes.LIGHT -> Themes.light
                PThemes.SYSTEM -> Themes.system
                else -> {
                    println("error, unknown theme")
                    Themes.system
                }
            }
            setting.firstStart.value = pSetting.pFirstStart
            setting.launchAtStartUp.value = pSetting.pLaunchAtStartUp
            setting.degree.value = pSetting.pDegree
        }

        fun writeSetting() {
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
                pTheme = when (setting.theme.value) {
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
            return includeFolder.resolve(SETTING_FILE)
        }
    }

}