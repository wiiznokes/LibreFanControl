package proto

import Application.Api.api
import Application.Api.scope
import FState.settings
import com.google.protobuf.NullValue
import kotlinx.coroutines.launch
import model.ConfInfo
import model.Languages
import model.Settings
import model.Themes
import proto.SettingsDir.createDirs
import proto.SettingsDir.settingsFile
import proto.generated.pSettings.*
import java.io.File

object SettingsDir {

    private val dir = File("C:\\ProgramData\\FanControl")

    private val confDir = dir.resolve("conf")

    fun createDirs() {
        if (!dir.exists()) {
            dir.mkdir()
        }
        if (!confDir.exists()) {
            confDir.mkdir()
        }
    }

    val settingsFile = dir.resolve("settings")

    fun getConfFile(confId: String): File = confDir
        .resolve(confId)
}


class SettingsHelper {

    companion object {

        fun isSettings(): Boolean {
            createDirs()
            return settingsFile.exists()
        }


        fun loadSettings() {
            val pSettings = PSettings.parseFrom(settingsFile.readBytes())

            parsePSetting(pSettings).let {
                settings.language.value = it.language.value
                settings.confId.value = it.confId.value
                settings.confInfoList.apply {
                    clear()
                    addAll(it.confInfoList)
                }
                settings.updateDelay.value = it.updateDelay.value
                settings.theme.value = it.theme.value
                settings.firstStart.value = it.firstStart.value
                settings.launchAtStartUp.value = it.launchAtStartUp.value
                settings.degree.value = it.degree.value
            }
        }


        fun writeSettings(notifyService: Boolean = false) {
            createPSetting(settings).let {
                settingsFile.writeBytes(it.toByteArray())
            }
            if (notifyService) {
                scope.launch {
                    api.settingsChange()
                }
            }
        }


        fun parsePSetting(pSetting: PSettings): Settings =
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


        fun createPSetting(settings: Settings): PSettings =
            pSettings {
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