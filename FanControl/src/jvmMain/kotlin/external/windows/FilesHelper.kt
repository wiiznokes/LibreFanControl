package external.windows

import java.io.File

/**
 * @return should call removeDir
 */
fun copyFiles(srcDir: File, destDir: File): Boolean {
    if (!srcDir.exists()) {
        return false
    }

    srcDir.listFiles()?.forEach { file ->
        val destFile = File(destDir, file.name)
        file.copyTo(destFile, true)
    }
    return true
}


fun removeDir(dir: File) {
    if (dir.exists() && dir.isDirectory) {
        dir.listFiles()?.forEach {
            if (it.isDirectory){
                removeDir(File(it.path))
            }
            else {
                it.delete()
            }
        }
        dir.delete()
    }
}
