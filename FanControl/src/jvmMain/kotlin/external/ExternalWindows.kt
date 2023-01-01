package external

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.HardwareType
import model.hardware.Sensor
import model.item.ControlItem
import utils.getAvailableId

class ExternalWindows : External {


    private val values: IntArray = IntArray(25) { 0 }
    override fun start(
        fans: MutableStateFlow<SnapshotStateList<Sensor>>,
        temps: MutableStateFlow<SnapshotStateList<Sensor>>,
        controls: MutableStateFlow<SnapshotStateList<ControlItem>>
    ) {
        try {
            System.loadLibrary("CppProxy")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        externalStart(values)

        super.start(fans, temps, controls)
    }

    override fun stop() {
        externalStop()
    }

    override fun getFan(fans: MutableStateFlow<SnapshotStateList<Sensor>>) {
        val result = externalGetFan()

        for (i in 0..(result.size - 1) / 3) {
            fans.update {
                it.add(
                    Sensor(
                        libIndex = result[i * 3].toInt(),
                        libId = result[(i * 3) + 1],
                        libName = result[(i * 3) + 2],
                        type = HardwareType.SensorType.H_S_FAN,
                        id = getAvailableId(it.map { sensor -> sensor.id })
                    )
                )
                it
            }
        }
    }

    override fun getTemp(temps: MutableStateFlow<SnapshotStateList<Sensor>>) {
        val result = externalGetTemp()

        for (i in 0..(result.size - 1) / 3) {
            temps.update {
                it.add(
                    Sensor(
                        libIndex = result[i * 3].toInt(),
                        libId = result[(i * 3) + 1],
                        libName = result[(i * 3) + 2],
                        type = HardwareType.SensorType.H_S_TEMP,
                        id = getAvailableId(it.map { sensor -> sensor.id })
                    )
                )
                it
            }
        }
    }

    override fun getControl(controls: MutableStateFlow<SnapshotStateList<ControlItem>>) {
        val result = externalGetControl()

        for (i in 0..(result.size - 1) / 3) {
            controls.update {
                it.add(
                    ControlItem(
                        libIndex = result[i * 3].toInt(),
                        libId = result[(i * 3) + 1],
                        libName = result[(i * 3) + 2],
                        name = result[(i * 3) + 2],
                        itemId = getAvailableId(
                            it.map { control -> control.itemId }
                        )
                    )
                )
                it
            }
        }
    }

    override fun updateFan(fans: MutableStateFlow<SnapshotStateList<Sensor>>) {
        externalUpdateFan()

        for (i in fans.value.indices) {
            fans.update {
                it[i] = it[i].copy(
                    value = values[it[i].libIndex]
                )
                it
            }
        }
    }

    override fun updateTemp(temps: MutableStateFlow<SnapshotStateList<Sensor>>) {
        externalUpdateTemp()

        for (i in temps.value.indices) {
            temps.update {
                it[i] = it[i].copy(
                    value = values[it[i].libIndex]
                )
                it
            }
        }
    }

    override fun updateControl(controls: MutableStateFlow<SnapshotStateList<ControlItem>>) {
        externalUpdateControl()

        for (i in controls.value.indices) {
            controls.update {
                it[i] = it[i].copy(
                    value = values[it[i].libIndex]
                )
                it
            }
        }
    }

    override fun setControl(libIndex: Int, isAuto: Boolean, value: Int?) {
        externalSetControl(
            libIndex = libIndex,
            isAuto = isAuto,
            value = value ?: 100
        )
    }

    private external fun externalStart(values: IntArray)
    private external fun externalStop()
    private external fun externalGetFan(): Array<String>
    private external fun externalGetTemp(): Array<String>
    private external fun externalGetControl(): Array<String>
    private external fun externalUpdateFan()
    private external fun externalUpdateTemp()
    private external fun externalUpdateControl()
    private external fun externalSetControl(libIndex: Int, isAuto: Boolean, value: Int)
}