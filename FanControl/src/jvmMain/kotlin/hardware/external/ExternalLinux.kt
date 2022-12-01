package hardware.external

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.Control
import model.Fan
import model.Temp
import kotlin.random.Random

class ExternalLinux : External {
    override fun start() {
    }

    override fun stop() {
    }

    override fun getFan(): SnapshotStateList<Fan> {
        val fans = mutableStateListOf<Fan>()
        fans.add(
            Fan(
                index = 0,
                id = "fan1",
                libName = "fan1"
            )
        )
        fans.add(
            Fan(
                index = 1,
                id = "fan2",
                libName = "fan2"
            )
        )
        fans.add(
            Fan(
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

    override fun getControl(): SnapshotStateList<Control> {
        val controls = mutableStateListOf<Control>()
        controls.add(
            Control(
                index = 0,
                id = "fan1",
                libName = "fan1"
            )
        )
        controls.add(
            Control(
                index = 1,
                id = "fan2",
                libName = "fan2"
            )
        )
        controls.add(
            Control(
                index = 2,
                id = "fan3",
                libName = "fan3"
            )
        )
        return controls
    }

    override fun updateFan(fans: SnapshotStateList<Fan>) {
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

    override fun updateControl(controls: SnapshotStateList<Control>) {
        for (i in controls.indices) {
            controls[i] = controls[i].copy(
                value = Random.nextInt(0, 100)
            )
        }
    }

    override fun setControl(id: Int, isAuto: Boolean, value: Int) {

    }
}