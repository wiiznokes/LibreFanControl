package external

import androidx.compose.runtime.snapshots.SnapshotStateList
import model.HardwareType
import model.hardware.Sensor
import model.item.ControlItem
import utils.getAvailableId
import utils.getAvailableName
import kotlin.random.Random

class ExternalLinux : External {

    override fun stop() {
    }

    override fun getFan(fans: SnapshotStateList<Sensor>) {
        for (i in 0..3) {
            fans.add(
                Sensor(
                    libIndex = i,
                    libId = "fan${i + 1}",
                    libName = "fan lib${i + 1}",
                    type = HardwareType.SensorType.H_S_FAN,
                    id = getAvailableId(fans.map { it.id })
                )
            )
        }
    }

    override fun getTemp(temps: SnapshotStateList<Sensor>) {
        for (i in 0..3) {
            temps.add(
                Sensor(
                    libIndex = i,
                    libId = "temp${i + 1}",
                    libName = "temp lib${i + 1}",
                    type = HardwareType.SensorType.H_S_TEMP,
                    id = getAvailableId(temps.map { it.id })
                )
            )
        }
    }

    override fun getControl(controls: SnapshotStateList<ControlItem>) {
        for (i in 0..3) {
            controls.add(
                ControlItem(
                    name = getAvailableName(controls.map { it.name }, "control"),
                    libIndex = i,
                    libId = "fan${i + 1}",
                    libName = "control lib${i + 1}",
                    itemId = getAvailableId(controls.map { it.itemId })
                )
            )
        }
    }

    override fun updateFan(fans: SnapshotStateList<Sensor>) {
        for (i in fans.indices) {
            fans[i] = fans[i].copy(
                value = Random.nextInt(0, 4000)
            )
        }
    }


    override fun updateTemp(temps: SnapshotStateList<Sensor>) {
        for (i in temps.indices) {
            temps[i] = temps[i].copy(
                value = Random.nextInt(0, 100)
            )
        }
    }

    override fun updateControl(controls: SnapshotStateList<ControlItem>) {
        for (i in controls.indices) {
            controls[i] = controls[i].copy(
                value = Random.nextInt(0, 100)
            )
        }
    }

    override fun setControl(libIndex: Int, isAuto: Boolean, value: Int?) {}
}