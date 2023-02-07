package settings

import proto.generated.setting.*
import java.io.File

class SettingHelper {



    fun initSetting() {
        val setting = setting {
            language = Languages.EN
            configId = nullableId {
                null_
            }
            updateDelay = 2
            theme = Themes.LIGHT
            firstStart = false
            launchAtStartUp = false
            degree = true
            exitOnClose = true
            exitOnCloseSet = true
        }

        val bytes = setting.toByteArray()

        val file = getFile()
        file.writeBytes(bytes)

    }

    fun getSetting() {
        val file = getFile()

        val bytes = file.readBytes()

        val setting = Setting.parseFrom(bytes)

        println(setting.theme.name)
    }

    private fun getFile(): File {
        val includeFolder = File(System.getProperty("compose.application.resources.dir"))

        println(includeFolder)

        return includeFolder.resolve("conf/setting")
    }
}