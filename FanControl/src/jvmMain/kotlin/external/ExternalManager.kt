package external

import SensorLists
import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.item.ControlItem

class ExternalManager(
    private val controlItemList: SnapshotStateList<ControlItem> = State.controlItemList,
    private val sensorLists: SensorLists = State.sensorLists
) {

    private val external: External = when (getOS()) {
        OS.WINDOWS -> ExternalWindows()
        OS.LINUX -> ExternalLinux()
        OS.UNSUPPORTED -> throw Exception("unsupported OS")
    }

    fun start() {
        external.start(sensorLists.fanList, sensorLists.tempList, controlItemList)
        println("start lib : success")
    }

    fun stop() {
        external.stop()
        println("stop lib : success")
    }

    fun updateFan() {
        external.updateFan(sensorLists.fanList)
        //println("updateFan : success")

    }

    fun updateTemp() {
        external.updateTemp(sensorLists.tempList)
        //println("updateTemp : success")
    }

    fun updateControl() {
        external.updateControl(controlItemList)
        //println("updateControl : success")
    }

    fun setControl(libIndex: Int, isAuto: Boolean, value: Int?) {
        //external.setControl(libIndex, isAuto, value)
        //println("setControl : success")
        //println("set control: index = $libIndex, isAuto = $isAuto, value = $value")
    }

}