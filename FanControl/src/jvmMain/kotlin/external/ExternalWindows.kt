package external

import androidx.compose.runtime.snapshots.SnapshotStateList
import model.HardwareType
import model.hardware.Sensor
import model.item.ControlItem
import utils.getAvailableId

class ExternalWindows : External {


    private val values: IntArray = IntArray(25) { 0 }
    override fun start(
        fans: SnapshotStateList<Sensor>,
        temps: SnapshotStateList<Sensor>,
        controls: SnapshotStateList<ControlItem>
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

    override fun getFan(fans: SnapshotStateList<Sensor>) {
        val result = externalGetFan()

        for (i in 0..(result.size - 1) / 3) {
            fans.add(
                Sensor(
                    libIndex = result[i * 3].toInt(),
                    libId = result[(i * 3) + 1],
                    libName = result[(i * 3) + 2],
                    type = HardwareType.SensorType.H_S_FAN,
                    id = getAvailableId(fans.map { it.id })
                )
            )
        }
    }

    override fun getTemp(temps: SnapshotStateList<Sensor>) {
        val result = externalGetTemp()

        for (i in 0..(result.size - 1) / 3) {
            temps.add(
                Sensor(
                    libIndex = result[i * 3].toInt(),
                    libId = result[(i * 3) + 1],
                    libName = result[(i * 3) + 2],
                    type = HardwareType.SensorType.H_S_TEMP,
                    id = getAvailableId(temps.map { it.id })
                )
            )
        }
    }

    override fun getControl(controls: SnapshotStateList<ControlItem>) {
        val result = externalGetControl()

        for (i in 0..(result.size - 1) / 3) {

            controls.add(
                ControlItem(
                    libIndex = result[i * 3].toInt(),
                    libId = result[(i * 3) + 1],
                    libName = result[(i * 3) + 2],
                    name = result[(i * 3) + 2],
                    itemId = getAvailableId(
                        controls.map { it.itemId }
                    )
                )
            )

        }
    }

    override fun updateFan(fans: SnapshotStateList<Sensor>) {
        externalUpdateFan()

        for (i in fans.indices) {
            fans[i] = fans[i].copy(
                value = values[fans[i].libIndex]
            )
        }
    }

    override fun updateTemp(temps: SnapshotStateList<Sensor>) {
        externalUpdateTemp()
        for (i in temps.indices) {
            temps[i] = temps[i].copy(
                value = values[temps[i].libIndex]
            )
        }
    }

    override fun updateControl(controls: SnapshotStateList<ControlItem>) {
        externalUpdateControl()

        for (i in controls.indices) {
            controls[i] = controls[i].copy(
                value = values[controls[i].libIndex]
            )
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