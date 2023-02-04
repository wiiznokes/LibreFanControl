package external.linux

import State.hControls
import State.hFans
import State.hTemps
import external.External
import model.HardwareType
import model.hardware.Control
import model.hardware.Sensor
import utils.Id.Companion.getAvailableId
import kotlin.random.Random

class ExternalLinux : External {

    override fun start() {
    }

    override fun close() {}

    override fun setControls() {
        for (i in 0..3) {
            hControls.add(
                Control(
                    name = "control lib${i + 1}",
                    id = getAvailableId(hControls.map { it.id })
                )
            )
        }
    }


    override fun setFans() {
        for (i in 0..3) {
            hFans.add(
                Sensor(
                    name = "fan lib${i + 1}",
                    type = HardwareType.SensorType.H_S_FAN,
                    id = getAvailableId(hFans.map { it.id })
                )
            )
        }
    }

    override fun setTemps() {
        for (i in 0..3) {
            hTemps.add(
                Sensor(
                    name = "temp lib${i + 1}",
                    type = HardwareType.SensorType.H_S_TEMP,
                    id = getAvailableId(hTemps.map { it.id })
                )
            )
        }
    }


    override fun updateControls() {
        for (i in hControls.indices) {
            hControls[i] = hControls[i].copy(
                value = UseForTest.newValue(hControls[i].value)
            )
        }
    }


    override fun updateFans() {
        for (i in hFans.indices) {
            hFans[i] = hFans[i].copy(
                value = Random.nextInt(0, 4000)
            )
        }
    }


    override fun updateTemps() {
        for (i in hTemps.indices) {
            hTemps[i] = hTemps[i].copy(
                value = UseForTest.newValue(hTemps[i].value)
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