package external

import external.linux.ExternalLinux
import external.windows.ExternalWindows


/**
 * This class is used to fetch sensor value, and set fan speed.
 * It provides an abstraction for handling Linux and Windows
 * with the External interface
 */
object ExternalManager : External {

    private val external: External = when (getOS()) {
        OS.windows -> ExternalWindows()
        OS.linux -> ExternalLinux()
        OS.unsupported -> throw Exception("unsupported OS")
    }

    override fun start() {
        external.start()

        setControls()
        setFans()
        setTemps()

        println("start lib : success")
    }

    override fun close() {
        external.close()
        println("stop lib : success")
    }


    override fun setControls() {
        external.setControls()
    }

    override fun setFans() {
        external.setFans()
    }

    override fun setTemps() {
        external.setTemps()
    }

    override fun updateControls() {
        external.updateControls()
    }

    override fun updateFans() {
        external.updateFans()
    }

    override fun updateTemps() {
        external.updateTemps()
    }

    override fun reloadSetting() {
        external.reloadSetting()
    }

    override fun reloadConfig(id: Long?) {
        external.reloadConfig(id)
    }

}