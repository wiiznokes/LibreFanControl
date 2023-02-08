package proto

import State.settings
import com.google.protobuf.NullValue
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


        fun loadSettings() {
            settings = with(getSettingsFile()) {
                PSetting.parseFrom(readBytes()).let {
                    parsePSetting(it)
                }
            }
        }



        fun writeSettings() {
            createPSetting(settings).let {
                with(getSettingsFile()) {
                    writeBytes(it.toByteArray())
                }
            }
        }





        private fun getSettingsFile(): File = File(System.getProperty("compose.application.resources.dir"))
            .resolve(SETTING_FILE)






        fun parsePSetting(pSetting: PSetting): Settings =
            Settings(
                language = when (pSetting.pLanguage) {
                    PLanguages.EN -> Languages.en
                    PLanguages.FR -> Languages.fr
                    else -> {
                        println("error, unknown language")
                        Languages.en
                    }
                },
                confId = nullableToNull(pSetting.pConfId),
                confInfoList = pSetting.pConfInfosList.map { confInfo ->
                    ConfInfo(
                        id = confInfo.pId,
                        name = confInfo.pName
                    )

                },
                updateDelay = pSetting.pUpdateDelay,
                theme = when (pSetting.pTheme) {
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



        fun createPSetting(settings: Settings): PSetting =
            pSetting {
                pLanguage = when (settings.language.value) {
                    Languages.en -> PLanguages.EN
                    Languages.fr -> PLanguages.FR
                }
                pConfId = nullToNullable(settings.confId.value)
                settings.confInfoList.forEach { confInfo ->
                    pConfInfos.add(
                        pConfInfo {
                            pId = confInfo.id
                            pName = confInfo.name.value
                        }
                    )
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
            }
    }
}




fun nullableToNull(nullableId: NullableId): String? =
    when (nullableId.kindCase) {
        NullableId.KindCase.PID -> nullableId.pId
        NullableId.KindCase.NULL -> null
        else -> throw ProtoException("Nullable id not set")
    }

fun nullToNullable(id: String?): NullableId =
    nullableId {
        when (id) {
            null -> null_ = NullValue.NULL_VALUE
            else -> pId = id
        }
    }