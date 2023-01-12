package external.linux

import androidx.compose.runtime.snapshots.SnapshotStateList
import external.External
import model.HardwareType
import model.hardware.Sensor
import model.item.control.Control
import utils.Id.Companion.getAvailableId
import utils.Name.Companion.getAvailableName
import kotlin.random.Random

class ExternalLinux : External {
    override fun stop() { }


    override fun setFanList(fanList: SnapshotStateList<Sensor>) {
        for (i in 0..3) {
            fanList.add(
                Sensor(
                    libIndex = i,
                    libId = "fan${i + 1}",
                    libName = "fan lib${i + 1}",
                    type = HardwareType.SensorType.H_S_FAN,
                    id = getAvailableId(fanList.map { it.id })
                )
            )
        }
    }

    override fun setTempList(tempList: SnapshotStateList<Sensor>) {
        for (i in 0..3) {
            tempList.add(
                Sensor(
                    libIndex = i,
                    libId = "temp${i + 1}",
                    libName = "temp lib${i + 1}",
                    type = HardwareType.SensorType.H_S_TEMP,
                    id = getAvailableId(tempList.map { it.id })
                )
            )
        }
    }

    override fun setControlList(controlList: SnapshotStateList<Control>) {
        for (i in 0..3) {
            controlList.add(
                Control(
                    name = getAvailableName(controlList.map { it.name }, "control"),
                    libIndex = i,
                    libId = "fan${i + 1}",
                    libName = "control lib${i + 1}",
                    id = getAvailableId(controlList.map { it.id })
                )
            )
        }
    }

    override fun updateFanList(fanList: SnapshotStateList<Sensor>) {
        for (i in fanList.indices) {
            fanList[i] = fanList[i].copy(
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

    override fun updateTempList(tempList: SnapshotStateList<Sensor>) {
        for (i in tempList.indices) {
            val temp = tempList[i]
            tempList[i] = temp.copy(
                value = newValue(temp.value)
            )
        }
    }

    override fun updateControlList(controlList: SnapshotStateList<Control>) {
        setList.forEach {
            val index = controlList.indexOfFirst { control ->
                control.libIndex == it.libIndex
            }

            if (it.isAuto) {
                controlList[index] = controlList[index].copy(
                    value = 0
                )
            }
            else {
                controlList[index] = controlList[index].copy(
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