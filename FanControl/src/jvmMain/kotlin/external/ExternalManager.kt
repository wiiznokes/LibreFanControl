package external

import SensorLists
import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import external.linux.ExternalLinux
import external.windows.ExternalWindows
import model.hardware.Control


/**
 * This class is used to fetch sensor value, and set fan speed.
 * It provides an abstraction for handling Linux and Windows
 * with the External interface
 */
class ExternalManager(
    private val controlList: SnapshotStateList<Control> = State.hControls,
    private val sensorLists: SensorLists = State.hSensorsList
) {

    private val external: External = when (OS.linux) {
        OS.windows -> ExternalWindows()
        OS.linux -> ExternalLinux()
        OS.unsupported -> throw Exception("unsupported OS")
    }


    fun start() {
        external.start(sensorLists.hFans, sensorLists.hTemps, controlList)
        println("start lib : success")
    }


    fun stop() {
        external.close()
        println("stop lib : success")
    }

    fun updateFans() {
        external.setUpdateFans(sensorLists.hFans)
        //println("updateFan : success")

    }

    fun updateTemps() {
        external.setUpdateTemps(sensorLists.hTemps)
        //println("updateTemp : success")
    }

    fun updateControls() {
        external.setUpdateControls(controlList)
        //println("updateControl : success")
    }

}