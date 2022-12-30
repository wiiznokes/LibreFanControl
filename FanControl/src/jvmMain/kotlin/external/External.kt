package external

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.hardware.Sensor
import model.item.ControlItem

interface External {

    fun start(
        fans: MutableStateFlow<SnapshotStateList<Sensor>>,
        temps: MutableStateFlow<SnapshotStateList<Sensor>>,
        controls: MutableStateFlow<SnapshotStateList<ControlItem>>
    ) {
        getFan(fans)
        getTemp(temps)
        getControl(controls)
    }

    fun stop()

    fun getFan(fans: MutableStateFlow<SnapshotStateList<Sensor>>)

    fun getTemp(temps: MutableStateFlow<SnapshotStateList<Sensor>>)

    fun getControl(controls: MutableStateFlow<SnapshotStateList<ControlItem>>)

    fun updateFan(
        fans: MutableStateFlow<SnapshotStateList<Sensor>>
    )

    fun updateTemp(
        temps: MutableStateFlow<SnapshotStateList<Sensor>>
    )

    fun updateControl(
        controls: MutableStateFlow<SnapshotStateList<ControlItem>>
    )

    fun setControl(libIndex: Int, isAuto: Boolean, value: Int?)
}