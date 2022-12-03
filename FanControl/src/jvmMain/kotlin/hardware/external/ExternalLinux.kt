package hardware.external

import FlagGlobalItemType
import FlagSpecifyItemType
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.control.Control
import model.hardware.sensor.Fan
import model.hardware.sensor.Temp
import kotlin.random.Random

class ExternalLinux : External {

    override fun stop() {
    }

    override fun getFan(fans: MutableStateFlow<SnapshotStateList<Fan>>) {
        fans.value.add(
            Fan(
                libIndex = 0,
                id = "fan1",
                libName = "fan1",
                globalType = FlagGlobalItemType.FAN_SENSOR,
                specifyType = FlagSpecifyItemType.FAN_SENSOR
            )
        )
        fans.value.add(
            Fan(
                libIndex = 1,
                id = "fan2",
                libName = "fan2",
                globalType = FlagGlobalItemType.FAN_SENSOR,
                specifyType = FlagSpecifyItemType.FAN_SENSOR
            )
        )
        fans.value.add(
            Fan(
                libIndex = 2,
                id = "fan3",
                libName = "fan3",
                globalType = FlagGlobalItemType.FAN_SENSOR,
                specifyType = FlagSpecifyItemType.FAN_SENSOR
            )
        )
    }

    override fun getTemp(temps: MutableStateFlow<SnapshotStateList<Temp>>) {
        temps.value.add(
            Temp(
                libIndex = 0,
                id = "fan1",
                libName = "fan1",
                globalType = FlagGlobalItemType.TEMP_SENSOR,
                specifyType = FlagSpecifyItemType.TEMP_SENSOR
            )
        )
        temps.value.add(
            Temp(
                libIndex = 1,
                id = "fan2",
                libName = "fan2",
                globalType = FlagGlobalItemType.TEMP_SENSOR,
                specifyType = FlagSpecifyItemType.TEMP_SENSOR
            )
        )
        temps.value.add(
            Temp(
                libIndex = 2,
                id = "fan3",
                libName = "fan3",
                globalType = FlagGlobalItemType.TEMP_SENSOR,
                specifyType = FlagSpecifyItemType.TEMP_SENSOR
            )
        )
    }

    override fun getControl(controls: MutableStateFlow<SnapshotStateList<Control>>) {
        controls.value.add(
            Control(
                libIndex = 0,
                id = "fan1",
                libName = "fan1",
                globalType = FlagGlobalItemType.FAN_CONTROL,
                specifyType = FlagSpecifyItemType.FAN_CONTROL
            )
        )
        controls.value.add(
            Control(
                libIndex = 1,
                id = "fan2",
                libName = "fan2",
                globalType = FlagGlobalItemType.FAN_CONTROL,
                specifyType = FlagSpecifyItemType.FAN_CONTROL
            )
        )
        controls.value.add(
            Control(
                libIndex = 2,
                id = "fan3",
                libName = "fan3",
                globalType = FlagGlobalItemType.FAN_CONTROL,
                specifyType = FlagSpecifyItemType.FAN_CONTROL
            )
        )
    }

    override fun updateFan(
        fans: MutableStateFlow<SnapshotStateList<Fan>>,
        fans2: MutableStateFlow<SnapshotStateList<Fan>>
    ) {

        for (i in fans.value.indices) {
            fans.update {
                fans.value[i] = fans.value[i].copy(
                    value = Random.nextInt(0, 4000)
                )
                it
            }
        }
    }


    override fun updateTemp(
        temps: MutableStateFlow<SnapshotStateList<Temp>>,
        temps2: MutableStateFlow<SnapshotStateList<Temp>>
    ) {
        for (i in temps.value.indices) {
            temps.update {
                temps.value[i] = temps.value[i].copy(
                    value = Random.nextInt(0, 100)
                )
                it
            }
        }
    }

    override fun updateControl(
        controls: MutableStateFlow<SnapshotStateList<Control>>,
        controls2: MutableStateFlow<SnapshotStateList<Control>>
    ) {
        for (i in controls.value.indices) {
            controls.update {
                controls.value[i] = controls.value[i].copy(
                    value = Random.nextInt(0, 100)
                )
                it
            }
        }
    }

    override fun setControl(index: Int, isAuto: Boolean, value: Int) {

    }
}