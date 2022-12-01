package hardware.external

import androidx.compose.runtime.snapshots.SnapshotStateList
import model.Control
import model.Fan
import model.Temp

class ExternalManager(os: OS) {

    private val external: External = when (os) {
        OS.WINDOWS -> ExternalWindows()
        OS.LINUX -> ExternalLinux()
        OS.UNSUPPORTED -> throw Exception("unsupported OS")
    }

    fun start() {
        external.start()
        println("start lib : success")
    }

    fun stop() {
        external.stop()
        println("stop lib : success")
    }

    fun getFan(): SnapshotStateList<Fan> {
        val fans = external.getFan()
        println("getFan : success")
        return fans
    }

    fun getTemp(): SnapshotStateList<Temp> {
        val temps = external.getTemp()
        println("getTemp : success")
        return temps
    }

    fun getControl(): SnapshotStateList<Control> {
        val controls = external.getControl()
        println("getControl : success")
        return controls
    }

    fun updateFan(fans: SnapshotStateList<Fan>) {
        external.updateFan(fans)
        println("updateFan : success")

    }

    fun updateTemp(temps: SnapshotStateList<Temp>) {
        external.updateTemp(temps)
        println("updateTemp : success")
    }

    fun updateControl(controls: SnapshotStateList<Control>) {
        external.updateControl(controls)
        println("updateControl : success")
    }

    fun setControl(id: Int, isAuto: Boolean, value: Int) {
        external.setControl(id, isAuto, value)
        println("setControl : success")
    }
}