package external.linux

import androidx.compose.runtime.snapshots.SnapshotStateList
import external.External
import model.HardwareType
import model.hardware.Control
import model.hardware.Sensor
import utils.Id.Companion.getAvailableId
import kotlin.random.Random

class ExternalLinux : External {

    override fun start(
        fans: SnapshotStateList<Sensor>,
        temps: SnapshotStateList<Sensor>,
        controls: SnapshotStateList<Control>
    ) {
    }

    override fun close() {}

    override fun setControls(controls: SnapshotStateList<Control>) {
        for (i in 0..3) {
            controls.add(
                Control(
                    name = "control lib${i + 1}",
                    id = getAvailableId(controls.map { it.id })
                )
            )
        }
    }


    override fun setFans(fans: SnapshotStateList<Sensor>) {
        for (i in 0..3) {
            fans.add(
                Sensor(
                    name = "fan lib${i + 1}",
                    type = HardwareType.SensorType.H_S_FAN,
                    id = getAvailableId(fans.map { it.id })
                )
            )
        }
    }

    override fun setTemps(temps: SnapshotStateList<Sensor>) {
        for (i in 0..3) {
            temps.add(
                Sensor(
                    name = "temp lib${i + 1}",
                    type = HardwareType.SensorType.H_S_TEMP,
                    id = getAvailableId(temps.map { it.id })
                )
            )
        }
    }


    override fun setUpdateControls(controls: SnapshotStateList<Control>) {
        for (i in controls.indices) {
            val control = controls[i]
            controls[i] = control.copy(
                value = UseForTest.newValue(control.value)
            )
        }
    }


    override fun setUpdateFans(fans: SnapshotStateList<Sensor>) {
        for (i in fans.indices) {
            fans[i] = fans[i].copy(
                value = Random.nextInt(0, 4000)
            )
        }
    }


    override fun setUpdateTemps(temps: SnapshotStateList<Sensor>) {
        for (i in temps.indices) {
            val temp = temps[i]
            temps[i] = temp.copy(
                value = UseForTest.newValue(temp.value)
            )
        }
    }

    override fun reloadSetting() {}
    override fun reloadConfig(id: Long?) {}
}


private class UseForTest {

    companion object {
        private var direction = true

        // emulate natural value
        fun newValue(value: Int): Int {
            val min = 30
            val max = 75
            val delta = Random.nextInt(0, 10)

            return if (direction) {
                (value + delta).let {
                    if (it > max) {
                        direction = false
                        max
                    } else it
                }
            } else {
                (value - delta).let {
                    if (it < min) {
                        direction = true
                        min
                    } else it
                }
            }
        }
    }
}