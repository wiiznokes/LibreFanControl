package external.windows

import java.io.File

/**
 *
 * @return should call removeDir
 */
fun copyFiles(srcDir: File, destDir: File): Boolean {
    if (!srcDir.exists()) {
        println("${srcDir.name} not exist")
        return false
    }

    srcDir.listFiles()?.forEach { file ->
        val destFile = File(destDir, file.name)
        file.copyTo(destFile, true)
        println("${file.name} copied")
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
                println("file ${it.name} removed")
            }
        }
        dir.delete()
        println("dir ${dir.name} removed")
    }
}
