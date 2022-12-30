package external

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.HardwareType
import model.ItemType
import model.hardware.Sensor
import model.item.ControlItem
import utils.getAvailableId
import kotlin.random.Random

class ExternalLinux : External {

    override fun stop() {
    }

    override fun getFan(fans: MutableStateFlow<SnapshotStateList<Sensor>>) {
        fans.update {
            it.add(
                Sensor(
                    libIndex = 0,
                    libId = "fan1",
                    libName = "fan1",
                    type = HardwareType.SensorType.H_S_FAN,
                    id = getAvailableId(it.map { sensor -> sensor.id })
                )
            )
            it
        }
        fans.update {
            it.add(
                Sensor(
                    libIndex = 1,
                    libId = "fan2",
                    libName = "fan2",
                    type = HardwareType.SensorType.H_S_FAN,
                    id = getAvailableId(it.map { sensor -> sensor.id })
                )
            )
            it
        }
        fans.update {
            it.add(
                Sensor(
                    libIndex = 2,
                    libId = "fan3",
                    libName = "fan3",
                    type = HardwareType.SensorType.H_S_FAN,
                    id = getAvailableId(it.map { sensor -> sensor.id })
                )
            )
            it
        }
    }

    override fun getTemp(temps: MutableStateFlow<SnapshotStateList<Sensor>>) {

        temps.update {
            it.add(
                Sensor(
                    libIndex = 0,
                    libId = "temp1",
                    libName = "temp1",
                    type = HardwareType.SensorType.H_S_TEMP,
                    id = getAvailableId(it.map { sensor -> sensor.id })
                )
            )
            it
        }
        temps.update {
            it.add(
                Sensor(
                    libIndex = 1,
                    libId = "temp2",
                    libName = "temp2",
                    type = HardwareType.SensorType.H_S_TEMP,
                    id = getAvailableId(it.map { sensor -> sensor.id })
                )
            )
            it
        }
        temps.update {
            it.add(
                Sensor(
                    libIndex = 2,
                    libId = "temp3",
                    libName = "temp3",
                    type = HardwareType.SensorType.H_S_TEMP,
                    id = getAvailableId(it.map { sensor -> sensor.id })
                )
            )
            it
        }
    }

    override fun getControl(controls: MutableStateFlow<SnapshotStateList<ControlItem>>) {
        controls.update {
            it.add(
                ControlItem(
                    libIndex = 0,
                    libId = "fan1",
                    libName = "fan1",
                    name = "fan1",
                    itemId = getAvailableId(it.map { item -> item.itemId })
                )
            )
            it
        }
        controls.update {
            it.add(
                ControlItem(
                    libIndex = 1,
                    libId = "fan2",
                    libName = "fan2",
                    name = "fan2",
                    itemId = getAvailableId(it.map { item -> item.itemId })
                )
            )
            it
        }
        controls.update {
            it.add(
                ControlItem(
                    libIndex = 2,
                    libId = "fan3",
                    libName = "fan3",
                    name = "fan3",
                    itemId = getAvailableId(it.map { item -> item.itemId })
                )
            )
            it
        }
    }

    override fun updateFan(fans: MutableStateFlow<SnapshotStateList<Sensor>>) {

        for (i in fans.value.indices) {
            fans.update {
                it[i] = it[i].copy(
                    value = Random.nextInt(0, 4000)
                )
                it
            }
        }
    }


    override fun updateTemp(temps: MutableStateFlow<SnapshotStateList<Sensor>>) {
        for (i in temps.value.indices) {
            temps.update {
                it[i] = it[i].copy(
                    value = Random.nextInt(0, 100)
                )
                it
            }
        }
    }

    override fun updateControl(controls: MutableStateFlow<SnapshotStateList<ControlItem>>) {
        for (i in controls.value.indices) {
            controls.update {
                it[i] = it[i].copy(
                    value = Random.nextInt(0, 100)
                )
                it
            }
        }
    }

    override fun setControl(libIndex: Int, isAuto: Boolean, value: Int?) {
        println("set control linux: index = $libIndex, isAuto = $isAuto, value = $value")
    }
}