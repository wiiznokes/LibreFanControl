package external

import SensorLists
import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import external.linux.ExternalLinux
import external.windows.ExternalWindows
import model.item.control.Control


/**
 * This class is used to fetch sensor value, and set fan speed.
 * It provides an abstraction for handling Linux and Windows
 * with the External interface
 */
class ExternalManager(
    private val controlList: SnapshotStateList<Control> = State.controlList,
    private val sensorLists: SensorLists = State.sensorLists,
    private val controlChangeList: SnapshotStateList<Boolean> = State.controlChangeList
) {

    private val external: External = when (getOS()) {
        OS.windows -> ExternalWindows()
        OS.linux -> ExternalLinux()
        OS.unsupported -> throw Exception("unsupported OS")
    }

    /**
     * load library and fetch sensors
     */
    fun start() {
        external.start(sensorLists.fanList, sensorLists.tempList, controlList, controlChangeList)
        println("start lib : success")
    }

    fun stop() {
        external.stop()
        println("stop lib : success")
    }

    fun updateFanList() {
        external.updateFanList(sensorLists.fanList)
        //println("updateFan : success")

    }

    fun updateTempList() {
        external.updateTempList(sensorLists.tempList)
        //println("updateTemp : success")
    }

    fun updateControlList() {
        external.updateControlList(controlList)
        //println("updateControl : success")
    }

    fun setControl(libIndex: Int, isAuto: Boolean, value: Int? = null) {
        //external.setControl(libIndex, isAuto, value)
        println("set control: index = $libIndex, isAuto = $isAuto, value = $value")
    }

}