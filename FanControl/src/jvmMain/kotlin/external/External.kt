package external

import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Sensor
import model.item.ControlItem

interface External {

    fun start(
        fans: SnapshotStateList<Sensor>,
        temps: SnapshotStateList<Sensor>,
        controls: SnapshotStateList<ControlItem>
    ) {
        getFan(fans)
        getTemp(temps)
        getControl(controls)
    }

    fun stop()

    fun getFan(fans: SnapshotStateList<Sensor>)

    fun getTemp(temps: SnapshotStateList<Sensor>)

    fun getControl(controls: SnapshotStateList<ControlItem>)

    fun updateFan(
        fans: SnapshotStateList<Sensor>
    )

    fun updateTemp(
        temps: SnapshotStateList<Sensor>
    )

    fun updateControl(
        controls: SnapshotStateList<ControlItem>
    )

    fun setControl(libIndex: Int, isAuto: Boolean, value: Int?)
}