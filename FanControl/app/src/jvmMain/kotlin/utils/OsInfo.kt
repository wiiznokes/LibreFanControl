package utils

import java.util.*


class OsException(msg: String) : Exception(msg)

@Suppress("EnumEntryName")
enum class OS {
    windows,
    linux,
    unsupported;
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