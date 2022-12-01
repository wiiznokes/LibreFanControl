package hardware.external

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.control.FanControl
import model.hardware.sensor.BaseSensor
import model.hardware.sensor.FanSpeed
import model.hardware.sensor.Temp
import kotlin.random.Random

class ExternalLinux : External {
    override fun start() {
    }

    override fun stop() {
    }

    override fun getFan(): SnapshotStateList<FanSpeed> {
        val fans = mutableStateListOf<FanSpeed>()
        fans.add(
            FanSpeed(
                index = 0,
                id = "fan1",
                libName = "fan1"
            )
        )
        fans.add(
            FanSpeed(
                index = 1,
                id = "fan2",
                libName = "fan2"
            )
        )
        fans.add(
            FanSpeed(
                index = 2,
                id = "fan3",
                libName = "fan3"
            )
        )

        return fans
    }

    override fun getTemp(): SnapshotStateList<Temp> {
        val temps = mutableStateListOf<Temp>()
        temps.add(
            Temp(
                index = 0,
                id = "fan1",
                libName = "fan1"
            )
        )
        temps.add(
            Temp(
                index = 1,
                id = "fan2",
                libName = "fan2"
            )
        )
        temps.add(
            Temp(
                index = 2,
                id = "fan3",
                libName = "fan3"
            )
        )

        return temps
    }

    override fun getControl(): SnapshotStateList<FanControl> {
        val controls = mutableStateListOf<FanControl>()
        controls.add(
            FanControl(
                index = 0,
                id = "fan1",
                libName = "fan1"
            )
        )
        controls.add(
            FanControl(
                index = 1,
                id = "fan2",
                libName = "fan2"
            )
        )
        controls.add(
            FanControl(
                index = 2,
                id = "fan3",
                libName = "fan3"
            )
        )
        return controls
    }

    override fun updateFan(fans: SnapshotStateList<FanSpeed>) {
        for (i in fans.indices) {
            fans[i] = fans[i].copy(
                value = Random.nextInt(0, 4000)
            )
        }
    }


    override fun updateTemp(temps: SnapshotStateList<Temp>) {
        for (i in temps.indices) {
            temps[i] = temps[i].copy(
                value = Random.nextInt(0, 100)
            )
        }
    }

    override fun updateControl(controls: SnapshotStateList<FanControl>) {
        for (i in controls.indices) {
            controls[i] = controls[i].copy(
                value = Random.nextInt(0, 100)
            )
        }
    }

    override fun setControl(id: String, isAuto: Boolean, value: Int) {

    }
}