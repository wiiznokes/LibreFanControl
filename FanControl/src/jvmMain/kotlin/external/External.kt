package external

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.hardware.control.Control
import model.hardware.sensor.Fan
import model.hardware.sensor.Temp

interface External {

    fun start(
        fans: MutableStateFlow<SnapshotStateList<Fan>>,
        temps: MutableStateFlow<SnapshotStateList<Temp>>,
        controls: MutableStateFlow<SnapshotStateList<Control>>
    ) {
        getFan(fans)
        getTemp(temps)
        getControl(controls)
    }

    fun stop()

    fun getFan(fans: MutableStateFlow<SnapshotStateList<Fan>>)

    fun getTemp(temps: MutableStateFlow<SnapshotStateList<Temp>>)

    fun getControl(controls: MutableStateFlow<SnapshotStateList<Control>>)

    fun updateFan(
        fans: MutableStateFlow<SnapshotStateList<Fan>>
    )

    fun updateTemp(
        temps: MutableStateFlow<SnapshotStateList<Temp>>
    )

    fun updateControl(
        controls: MutableStateFlow<SnapshotStateList<Control>>
    )

    fun setControl(libIndex: Int, isAuto: Boolean, value: Int)
}