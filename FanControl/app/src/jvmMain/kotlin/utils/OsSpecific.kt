package utils

import CustomError
import FState
import FState.settings
import ServiceState
import proto.SettingsHelper
import java.io.File

private const val DEBUG_SERVICE = false


fun getStartMode(launchAtStartUp: Boolean = settings.launchAtStartUp.value): String {
    return if (DEBUG_SERVICE) "Debug"
    else when (launchAtStartUp) {
        true -> "Automatic"
        false -> "Manual"
    }
}


fun getScript(scriptName: String): String {
    return File(System.getProperty("compose.application.resources.dir"))
        .resolve("scripts/service/$scriptName")
        .absolutePath
}


interface IOsSpecific {
    val settingsDir: File
    fun startService(): Boolean
    fun changeServiceStartMode(launchAtStartUp: Boolean): Boolean
    fun removeService(): Boolean
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

    override fun startService(): Boolean {

        return execScriptHelper(
            params = mutableListOf("powershell.exe", "-File", getScript("init.ps1"), getStartMode())
        )
    }

    override fun changeServiceStartMode(launchAtStartUp: Boolean): Boolean {

        return execScriptHelper(
            params = mutableListOf(
                "powershell.exe",
                "-File",
                getScript("change_start_mode.ps1"),
                getStartMode(launchAtStartUp)
            ),
            onSuccess = {
                settings.launchAtStartUp.value = launchAtStartUp
                SettingsHelper.writeSettings()
            }
        )
    }

    override fun removeService(): Boolean {
        return execScriptHelper(
            params = mutableListOf("powershell.exe", "-File", getScript("uninstall.ps1")),
            onSuccess = {
                FState.serviceState.value = ServiceState.ERROR
                settings.confId.value = null
                SettingsHelper.writeSettings()
            }
        )
    }

}


private class Linux : IOsSpecific {

    override val settingsDir: File = File(System.getProperty("user.home"), ".FanControl")
    override fun startService(): Boolean {

        return execScriptHelper(
            params = mutableListOf("bash", getScript("init.sh"), getStartMode())
        )
    }

    override fun changeServiceStartMode(launchAtStartUp: Boolean): Boolean {

        return execScriptHelper(
            params = mutableListOf("bash", getScript("change_start_mode.sh"), getStartMode(launchAtStartUp)),
            onSuccess = {
                settings.launchAtStartUp.value = launchAtStartUp
                SettingsHelper.writeSettings()
            }
        )
    }

    override fun removeService(): Boolean {
        return execScriptHelper(
            params = mutableListOf("bash", getScript("uninstall.sh")),
            onSuccess = {
                FState.serviceState.value = ServiceState.ERROR
                settings.confId.value = null
                SettingsHelper.writeSettings()
            }
        )
    }

}

private fun execScriptHelper(
    params: MutableList<String>,
    onSuccess: (() -> Unit)? = null
): Boolean {

    val res = ProcessBuilder(params)
        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()
        .waitFor()

    if (res == ErrorCode.SUCCESS.code) {
        onSuccess?.invoke()
    }

    return handleErrorCode(res)
}

private enum class ErrorCode(val code: Int) {
    SUCCESS(0),

    // may indicate an ExecutionPolicy exception
    UNSPECIFIED(1),
    DEFAULT(101),
    NEED_ADMIN(102),
    NOT_INSTALLED(103),
    NEED_RUNTIME(104)
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

        else -> throw Exception("Error code not known when execution init script: $code")
    }
}