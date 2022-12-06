package external

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.hardware.Control
import model.hardware.Sensor

interface External {

    fun start(
        fans: MutableStateFlow<SnapshotStateList<Sensor>>,
        temps: MutableStateFlow<SnapshotStateList<Sensor>>,
        controls: MutableStateFlow<SnapshotStateList<Control>>
    ) {
        getFan(fans)
        getTemp(temps)
        getControl(controls)
    }

    fun stop()

    fun getFan(fans: MutableStateFlow<SnapshotStateList<Sensor>>)

    fun getTemp(temps: MutableStateFlow<SnapshotStateList<Sensor>>)

    fun getControl(controls: MutableStateFlow<SnapshotStateList<Control>>)

    fun updateFan(
        fans: MutableStateFlow<SnapshotStateList<Sensor>>
    )

    fun updateTemp(
        temps: MutableStateFlow<SnapshotStateList<Sensor>>
    )

    fun updateControl(
        controls: MutableStateFlow<SnapshotStateList<Control>>
    )

    fun setControl(libIndex: Int, isAuto: Boolean, value: Int)
}