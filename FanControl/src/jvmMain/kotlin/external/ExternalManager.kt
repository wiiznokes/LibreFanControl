package external

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.hardware.control.Control
import model.hardware.sensor.Fan
import model.hardware.sensor.Temp

class ExternalManager(os: OS) {

    private val external: External = when (os) {
        OS.WINDOWS -> ExternalWindows()
        OS.LINUX -> ExternalLinux()
        OS.UNSUPPORTED -> throw Exception("unsupported OS")
    }

    fun start(
        fans: MutableStateFlow<SnapshotStateList<Fan>>,
        temps: MutableStateFlow<SnapshotStateList<Temp>>,
        controls: MutableStateFlow<SnapshotStateList<Control>>
    ) {
        external.start(fans, temps, controls)
        println("start lib : success")
    }

    fun stop() {
        external.stop()
        println("stop lib : success")
    }

    fun getFan(fans: MutableStateFlow<SnapshotStateList<Fan>>) {
        external.getFan(fans)
        println("getFan : success")
    }

    fun getTemp(temps: MutableStateFlow<SnapshotStateList<Temp>>) {
        external.getTemp(temps)
        println("getTemp : success")
    }

    fun getControl(controls: MutableStateFlow<SnapshotStateList<Control>>) {
        external.getControl(controls)
        println("getControl : success")
    }

    fun updateFan(
        fans: MutableStateFlow<SnapshotStateList<Fan>>,
        fans2: MutableStateFlow<SnapshotStateList<Fan>>
    ) {
        external.updateFan(fans, fans2)
        println("updateFan : success")

    }

    fun updateTemp(
        temps: MutableStateFlow<SnapshotStateList<Temp>>,
        temps2: MutableStateFlow<SnapshotStateList<Temp>>
    ) {
        external.updateTemp(temps, temps2)
        println("updateTemp : success")
    }

    fun updateControl(
        controls: MutableStateFlow<SnapshotStateList<Control>>,
        controls2: MutableStateFlow<SnapshotStateList<Control>>
    ) {
        external.updateControl(controls, controls2)
        println("updateControl : success")
    }

    fun setControl(index: Int, isAuto: Boolean, value: Int) {
        external.setControl(index, isAuto, value)
        println("setControl : success")
    }
}