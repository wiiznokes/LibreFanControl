package utils

import CustomError
import FState
import FState.settings
import proto.SettingsHelper
import ui.settings.getStartMode
import java.io.File

private const val DEBUG_SERVICE = false

interface IOsSpecific {


    val settingsDir: File
    fun startService(): Boolean

    fun changeServiceStartMode(launchAtStartUp: Boolean): Boolean
}


object OsSpecific {

    val os: IOsSpecific = when (getOS()) {
        OS.windows -> Windows()
        OS.linux -> Linux()

        OS.unsupported -> TODO()
    }

}


private class Windows : IOsSpecific {

    override val settingsDir: File = File("C:\\ProgramData\\FanControl")


    enum class ErrorCode(val code: Int) {
        SUCCESS(0),

        // may indicate an ExecutionPolicy exception
        UNSPECIFIED(1),
        DEFAULT(2),
        NEED_ADMIN(3),
        NOT_INSTALLED(4),
        NEED_RUNTIME(5)
    }


    override fun startService(): Boolean {
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

        val res = try {
            ProcessBuilder(command)
                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start()
                .waitFor()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return handleErrorCode(res)


    }

    override fun changeServiceStartMode(launchAtStartUp: Boolean): Boolean {

        val initScript = File(System.getProperty("compose.application.resources.dir"))
            .resolve("scripts/service/change_start_mode.ps1")
            .absolutePath

        val startMode = getStartMode(launchAtStartUp)

        val command = listOf(
            "powershell.exe",
            "-File",
            initScript,
            startMode
        )
        val res = ProcessBuilder(command)
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()

        if (res == ErrorCode.SUCCESS.code) {
            settings.launchAtStartUp.value = launchAtStartUp
            SettingsHelper.writeSettings()
        }

        return handleErrorCode(res)
    }

    private fun handleErrorCode(code: Int): Boolean {

        return when (code) {
            ErrorCode.SUCCESS.code -> true

            ErrorCode.UNSPECIFIED.code -> {
                FState.ui.showError(
                    CustomError(
                        content = Resources.getString("dialog/error_content/unspecified"),
                        copyContent = "Set-ExecutionPolicy -ExecutionPolicy RemoteSigned"
                    )
                )
                false
            }

            ErrorCode.DEFAULT.code -> {
                false
            }

            ErrorCode.NEED_ADMIN.code -> {
                FState.ui.showError(
                    error = CustomError(
                        content = Resources.getString("dialog/error_content/need_admin")
                    ),
                    copy = false
                )
                false
            }

            ErrorCode.NOT_INSTALLED.code -> {
                FState.ui.showError(
                    error = CustomError(
                        content = Resources.getString("dialog/error_content/not_installed")
                    )
                )
                false
            }

            ErrorCode.NEED_RUNTIME.code -> {
                FState.ui.showError(
                    CustomError(
                        content = Resources.getString("dialog/error_content/need_runtime"),
                        copyContent = "https://dotnet.microsoft.com/en-us/download/dotnet/7.0"
                    )
                )
                false
            }

            else -> throw Exception("Error code not known when execution init script")
        }
    }


}


private class Linux : IOsSpecific {

    override val settingsDir: File = File("etc/libreFanControl")
    override fun startService(): Boolean {
        return false
    }

    override fun changeServiceStartMode(launchAtStartUp: Boolean): Boolean {
        TODO("Not yet implemented")
    }

}