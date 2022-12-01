package hardware.external

import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.control.FanControl
import model.hardware.sensor.FanSpeed
import model.hardware.sensor.Temp

interface External {

    fun start()

    fun stop()

    fun getFan(): SnapshotStateList<FanSpeed>

    fun getTemp(): SnapshotStateList<Temp>

    fun getControl(): SnapshotStateList<FanControl>

    fun updateFan(fans: SnapshotStateList<FanSpeed>)
    fun updateTemp(temps: SnapshotStateList<Temp>)
    fun updateControl(controls: SnapshotStateList<FanControl>)

    fun setControl(id: String, isAuto: Boolean, value: Int)
}