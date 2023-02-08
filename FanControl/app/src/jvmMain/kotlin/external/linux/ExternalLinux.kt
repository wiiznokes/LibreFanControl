package external.linux

import State.hControls
import State.hFans
import State.hTemps
import external.External
import model.hardware.HControl
import model.hardware.HFan
import model.hardware.HTemp
import model.item.BaseI
import kotlin.random.Random

class ExternalLinux : External {

    override fun start() {
    }

    override fun close() {}

    override fun setControls() {
        for (i in 0..3) {
            hControls.add(
                HControl(
                    name = "control lib${i + 1}",
                    id = BaseI.getAvailableId(
                        list = hControls.map { it.id },
                        prefix = "controlLib"
                    )
                )
            )
        }
    }


    override fun setFans() {
        for (i in 0..3) {
            hFans.add(
                HFan(
                    name = "fan lib${i + 1}",
                    id = BaseI.getAvailableId(
                        list = hFans.map { it.id },
                        prefix = "fanLib"
                    )
                )
            )
        }
    }

    override fun setTemps() {
        for (i in 0..3) {
            hTemps.add(
                HTemp(
                    name = "temp lib${i + 1}",
                    id = BaseI.getAvailableId(
                        list = hTemps.map { it.id },
                        prefix = "tempLib"
                    )
                )
            )
        }
    }


    override fun updateControls() {
        for (i in hControls.indices) {
            hControls[i].value.value = UseForTest.newValue(hControls[i].value.value)
        }
    }


    override fun updateFans() {
        for (i in hFans.indices) {
            hFans[i].value.value = Random.nextInt(0, 4000)
        }
    }


    override fun updateTemps() {
        for (i in hTemps.indices) {
            hTemps[i].value.value = UseForTest.newValue(hTemps[i].value.value)
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