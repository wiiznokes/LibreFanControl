package external

import java.util.*


class OsException(msg: String) : Exception(msg)

@Suppress("EnumEntryName")
enum class OS {
    windows, linux, unsupported;

    companion object {
        infix fun from(value: String): OS = OS.values().first { it.name == value }
    }
}

fun getOS(): OS {
    val os = System.getProperty("os.name").lowercase(Locale.getDefault())
    return when {
        os.contains("win") -> OS.windows

        os.contains("nix") || os.contains("nux") || os.contains("aix") ->
            OS.linux

        else -> OS.unsupported
    }
}