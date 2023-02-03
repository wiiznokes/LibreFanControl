package external.linux

import androidx.compose.runtime.snapshots.SnapshotStateList
import external.External
import model.HardwareType
import model.hardware.Control
import model.hardware.Sensor
import utils.Id.Companion.getAvailableId
import kotlin.random.Random

class ExternalLinux : External {
    override fun stop() {}


    override fun setFans(fans: SnapshotStateList<Sensor>) {
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

    override fun setTemps(temps: SnapshotStateList<Sensor>) {
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

    override fun setControls(controls: SnapshotStateList<Control>) {
        for (i in 0..3) {
            controls.add(
                Control(
                    libIndex = i,
                    libId = "fan${i + 1}",
                    libName = "control lib${i + 1}",
                    id = getAvailableId(controls.map { it.id })
                )
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

    // used for testing
    private var direction = true
    private fun newValue(value: Int): Int {
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

    override fun setUpdateTemps(temps: SnapshotStateList<Sensor>) {
        for (i in temps.indices) {
            val temp = temps[i]
            temps[i] = temp.copy(
                value = newValue(temp.value)
            )
        }
    }

    override fun setUpdateControls(controls: SnapshotStateList<Control>) {
        setList.forEach {
            val index = controls.indexOfFirst { control ->
                control.libIndex == it.libIndex
            }

            if (it.isAuto) {
                controls[index] = controls[index].copy(
                    value = 0
                )
            } else {
                controls[index] = controls[index].copy(
                    value = it.value ?: 0
                )
            }
        }
        setList.clear()
    }

    private data class UpdateControl(
        val libIndex: Int,
        val isAuto: Boolean,
        val value: Int?
    )

    private val setList = mutableListOf<UpdateControl>()

    override fun setControl(libIndex: Int, isAuto: Boolean, value: Int?) {
        setList.add(UpdateControl(libIndex, isAuto, value))
    }
}