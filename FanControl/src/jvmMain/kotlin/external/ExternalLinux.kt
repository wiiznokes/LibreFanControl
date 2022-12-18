package external

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.ItemType
import model.hardware.Control
import model.hardware.Sensor
import kotlin.random.Random

class ExternalLinux : External {

    override fun stop() {
    }

    override fun getFan(fans: MutableStateFlow<SnapshotStateList<Sensor>>) {
        fans.value.add(
            Sensor(
                libIndex = 0,
                libId = "fan1",
                libName = "fan1",
                type = ItemType.SensorType.FAN,
            )
        )
        fans.value.add(
            Sensor(
                libIndex = 1,
                libId = "fan2",
                libName = "fan2",
                type = ItemType.SensorType.FAN,
            )
        )
        fans.value.add(
            Sensor(
                libIndex = 2,
                libId = "fan3",
                libName = "fan3",
                type = ItemType.SensorType.FAN,
            )
        )
    }

    override fun getTemp(temps: MutableStateFlow<SnapshotStateList<Sensor>>) {
        temps.value.add(
            Sensor(
                libIndex = 0,
                libId = "temp1",
                libName = "temp1",
                type = ItemType.SensorType.TEMP,
            )
        )
        temps.value.add(
            Sensor(
                libIndex = 1,
                libId = "temp2",
                libName = "temp2",
                type = ItemType.SensorType.TEMP,
            )
        )
        temps.value.add(
            Sensor(
                libIndex = 2,
                libId = "temp3",
                libName = "temp3",
                type = ItemType.SensorType.TEMP,
            )
        )
    }

    override fun getControl(controls: MutableStateFlow<SnapshotStateList<Control>>) {
        controls.value.add(
            Control(
                libIndex = 0,
                libId = "fan1",
                libName = "fan1"
            )
        )
        controls.value.add(
            Control(
                libIndex = 1,
                libId = "fan2",
                libName = "fan2",
            )
        )
        controls.value.add(
            Control(
                libIndex = 2,
                libId = "fan3",
                libName = "fan3"
            )
        )
    }

    override fun updateFan(fans: MutableStateFlow<SnapshotStateList<Sensor>>) {

        for (i in fans.value.indices) {
            fans.update {
                fans.value[i] = fans.value[i].copy(
                    value = Random.nextInt(0, 4000)
                )
                it
            }
        }
    }


    override fun updateTemp(temps: MutableStateFlow<SnapshotStateList<Sensor>>) {
        for (i in temps.value.indices) {
            temps.update {
                temps.value[i] = temps.value[i].copy(
                    value = Random.nextInt(0, 100)
                )
                it
            }
        }
    }

    override fun updateControl(controls: MutableStateFlow<SnapshotStateList<Control>>) {
        for (i in controls.value.indices) {
            controls.update {
                controls.value[i] = controls.value[i].copy(
                    value = Random.nextInt(0, 100)
                )
                it
            }
        }
    }

    override fun setControl(libIndex: Int, isAuto: Boolean, value: Int?) {

    }
}