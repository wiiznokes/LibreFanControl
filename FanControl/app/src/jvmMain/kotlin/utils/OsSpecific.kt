package utils

import FState.settings
import ui.settings.getStartMode
import java.io.File

private const val DEBUG_SERVICE = false
interface IOsSpecific {


    val settingsDir: File
    fun startService (): Int
}




object OsSpecific {

        val os: IOsSpecific = when(getOS()) {
            OS.windows -> Windows()
            OS.linux -> Linux()

            OS.unsupported -> TODO()
        }

}




private class Windows : IOsSpecific {

    override val settingsDir: File = File("C:\\ProgramData\\FanControl")



    override fun startService(): Int {
        val initScript = File(System.getProperty("compose.application.resources.dir"))
            .resolve("scripts/service/init.ps1")
            .absolutePath

        val startMode = if (DEBUG_SERVICE) "Debug"
        else
            getStartMode(settings.launchAtStartUp.value)

        val command = listOf(
            "powershell.exe",
            "-File",
            initScript,
            startMode
        )

        return ProcessBuilder(command)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()
    }


}





private class Linux: IOsSpecific {

    override val settingsDir: File = File("etc/libreFanControl")
    override fun startService(): Int {
        return 1
    }

}