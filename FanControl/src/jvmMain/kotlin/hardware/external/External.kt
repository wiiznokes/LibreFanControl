package hardware.external

import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.control.Control
import model.hardware.sensor.Fan
import model.hardware.sensor.Temp

interface External {

    fun start()

    fun stop()

    fun getFan(): SnapshotStateList<Fan>

    fun getTemp(): SnapshotStateList<Temp>

    fun getControl(): SnapshotStateList<Control>

    fun updateFan(fans: SnapshotStateList<Fan>)
    fun updateTemp(temps: SnapshotStateList<Temp>)
    fun updateControl(controls: SnapshotStateList<Control>)

    fun setControl(index: Int, isAuto: Boolean, value: Int)
}