package proto

import Application.Api.api
import Application.Api.scope
import ConfInfo
import FState.settings
import Languages
import Settings
import Themes
import com.google.protobuf.NullValue
import kotlinx.coroutines.launch
import proto.SettingsDir.settingsFile
import proto.generated.pSettings.*
import utils.OsSpecific
import java.io.File

object SettingsDir {

    private val settingsDir = OsSpecific.os.settingsDir

    private val confDir = settingsDir.resolve("conf")

    fun tryCreateConfDirsIfNecessary(): Boolean {
        if (!settingsDir.exists()) {
            if (!settingsDir.mkdirs()) {
                return false
            }
        }

        if (!confDir.exists()) {
            if (!confDir.mkdirs()) {
                return false
            }
        }

        return true
    }

    val settingsFile = settingsDir.resolve("settings")

    fun getConfFile(confId: String): File = confDir
        .resolve(confId)
}


class SettingsHelper {

    companion object {

        fun isSettings(): Boolean {
            return settingsFile.exists()
        }


        /**
         * Read settings file into PSettings
         * Parse PSettings into Settings
         * Copy Settings to global state
         */
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
                settings.versionInstalled.value = it.versionInstalled.value
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

        /**
         * Create new Settings class from PSettings
         */
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
                degree = pSetting.pDegree,
                valueDisableControl = pSetting.pValueDisableControl,
                versionInstalled = pSetting.pVersionInstalled
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
                pValueDisableControl = settings.valueDisableControl.value
                pVersionInstalled = settings.versionInstalled.value
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