package utils

import DEBUG
import FState
import FState.service
import FState.settings
import UiState
import proto.SettingsHelper
import java.io.File


fun getStartMode(launchAtStartUp: Boolean = settings.launchAtStartUp.value): String {
    return when (launchAtStartUp) {
        true -> "Automatic"
        false -> "Manual"
    }
}


fun getScript(scriptName: String): String {
    return File(System.getProperty("compose.application.resources.dir"))
        .resolve("scripts/$scriptName")
        .absolutePath
}


interface IOsSpecific {
    val settingsDir: File

    fun installService(version: String): Boolean


    fun startService(): Boolean
    fun changeStartModeService(launchAtStartUp: Boolean): Boolean
    fun uninstallService(): Boolean

    fun isAdmin(): Boolean

}

/**
 * Display pop error internally
 */
object OsSpecific {

    val os: IOsSpecific = when (getOS()) {
        OS.windows -> Windows()
        OS.linux -> Linux()
        OS.unsupported -> TODO()
    }
}


private class Windows : IOsSpecific {

    override val settingsDir: File = File("C:\\ProgramData\\LibreFanControl")

    override fun installService(version: String): Boolean {
        return execScriptHelper(
            params = mutableListOf("powershell.exe", "-File", getScript("install.ps1")),
            onSuccess = {
                settings.versionInstalled.value = version
                SettingsHelper.writeSettings()
            }
        )
    }

    override fun startService(): Boolean {

        return execScriptHelper(
            params = mutableListOf("powershell.exe", "-File", getScript("start.ps1"))
        )
    }

    override fun changeStartModeService(launchAtStartUp: Boolean): Boolean {

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

    override fun uninstallService(): Boolean {
        return execScriptHelper(
            params = mutableListOf("powershell.exe", "-File", getScript("uninstall.ps1")),
            onSuccess = {
                service.setErrorStatus()
                settings.confId.value = null
                settings.versionInstalled.value = "0.0.0"
                SettingsHelper.writeSettings()
            }
        )
    }

    override fun isAdmin(): Boolean {
        TODO("not needed at this point")
    }

}


private class Linux : IOsSpecific {

    override val settingsDir: File = File("/etc/LibreFanControl")
    override fun installService(version: String): Boolean {
        return execScriptHelper(
            params = mutableListOf("bash", getScript("install.sh")),
            onSuccess = {
                settings.versionInstalled.value = version
                SettingsHelper.writeSettings()
            }
        )
    }

    override fun startService(): Boolean {

        return execScriptHelper(
            params = mutableListOf("bash", getScript("start.sh"))
        )
    }

    override fun changeStartModeService(launchAtStartUp: Boolean): Boolean {

        return execScriptHelper(
            params = mutableListOf(
                "bash", getScript("change_start_mode.sh"),
                getStartMode(launchAtStartUp)
            ),
            onSuccess = {
                settings.launchAtStartUp.value = launchAtStartUp
                SettingsHelper.writeSettings()
            }
        )
    }

    override fun uninstallService(): Boolean {
        return execScriptHelper(
            params = mutableListOf("bash", getScript("uninstall.sh")),
            onSuccess = {
                service.setErrorStatus()
                settings.confId.value = null
                settings.versionInstalled.value = "0.0.0"
                SettingsHelper.writeSettings()
            }
        )
    }

    override fun isAdmin(): Boolean {
        return System.getenv()["USER"] == "root"
    }

}

private fun execScriptHelper(
    params: MutableList<String>,
    onSuccess: (() -> Unit)? = null
): Boolean {

    if (DEBUG)
        return true

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
    NEED_RUNTIME(104),
    INSTALLED(105),
}


private fun handleErrorCode(code: Int): Boolean {

    return when (code) {
        ErrorCode.SUCCESS.code -> true

        ErrorCode.UNSPECIFIED.code -> {
            FState.ui.showError(
                UiState.CustomError(
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
                error = UiState.CustomError(
                    content = Resources.getString("dialog/error_content/need_admin")
                ),
                copy = false
            )
            false
        }

        ErrorCode.NOT_INSTALLED.code -> {
            FState.ui.showError(
                error = UiState.CustomError(
                    content = Resources.getString("dialog/error_content/not_installed")
                )
            )
            false
        }

        ErrorCode.NEED_RUNTIME.code -> {
            FState.ui.showError(
                UiState.CustomError(
                    content = Resources.getString("dialog/error_content/need_runtime"),
                    copyContent = "https://dotnet.microsoft.com/en-us/download/dotnet/7.0"
                )
            )
            false
        }

        ErrorCode.INSTALLED.code -> {
            FState.ui.showError(
                UiState.CustomError(
                    content = "already installed"
                )
            )
            false
        }

        else -> {
            println("Error code not known when execution init script: $code")
            false
        }
    }
}