package external

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.hardware.Control
import model.hardware.Sensor

class ExternalManager {

    private val external: External = when (getOS()) {
        OS.WINDOWS -> ExternalWindows()
        OS.LINUX -> ExternalLinux()
        OS.UNSUPPORTED -> throw Exception("unsupported OS")
    }

    fun start(
        fans: MutableStateFlow<SnapshotStateList<Sensor>>,
        temps: MutableStateFlow<SnapshotStateList<Sensor>>,
        controls: MutableStateFlow<SnapshotStateList<Control>>
    ) {
        external.start(fans, temps, controls)
        println("start lib : success")
    }

    fun stop() {
        external.stop()
        println("stop lib : success")
    }

    fun getFan(fans: MutableStateFlow<SnapshotStateList<Sensor>>) {
        external.getFan(fans)
        println("getFan : success")
    }

    fun getTemp(temps: MutableStateFlow<SnapshotStateList<Sensor>>) {
        external.getTemp(temps)
        println("getTemp : success")
    }

    fun getControl(controls: MutableStateFlow<SnapshotStateList<Control>>) {
        external.getControl(controls)
        println("getControl : success")
    }

    fun updateFan(
        fans: MutableStateFlow<SnapshotStateList<Sensor>>
    ) {
        external.updateFan(fans)
        //println("updateFan : success")

    }

    fun updateTemp(
        temps: MutableStateFlow<SnapshotStateList<Sensor>>
    ) {
        external.updateTemp(temps)
        //println("updateTemp : success")
    }

    fun updateControl(
        controls: MutableStateFlow<SnapshotStateList<Control>>
    ) {
        external.updateControl(controls)
        //println("updateControl : success")
    }

    fun setControl(libIndex: Int, isAuto: Boolean, value: Int? = null) {
        external.setControl(libIndex, isAuto, value)
        println("setControl : success")
    }

}