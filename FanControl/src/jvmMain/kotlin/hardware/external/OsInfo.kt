package hardware.external

import java.util.*


enum class OS {
    WINDOWS, LINUX, UNSUPPORTED
}

fun getOS(): OS {
    val os = System.getProperty("os.name").lowercase(Locale.getDefault())
    return when {
        os.contains("win") -> OS.WINDOWS

        os.contains("nix") || os.contains("nux") || os.contains("aix") ->
            OS.LINUX

        else -> OS.UNSUPPORTED
    }
}