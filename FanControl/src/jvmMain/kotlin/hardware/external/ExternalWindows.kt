package hardware.external

import FlagGlobalItemType
import FlagSpecifyItemType
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.control.Control
import model.hardware.sensor.Fan
import model.hardware.sensor.Temp

class ExternalWindows : External {


    private val values: IntArray = IntArray(25) { 0 }
    override fun start(
        fans: MutableStateFlow<SnapshotStateList<Fan>>,
        temps: MutableStateFlow<SnapshotStateList<Temp>>,
        controls: MutableStateFlow<SnapshotStateList<Control>>
    ) {
        try {
            System.loadLibrary("CppProxy")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        externalStart(values)

        super.start(fans, temps, controls)
    }

    override fun stop() {
        externalStop()
    }

    override fun getFan(fans: MutableStateFlow<SnapshotStateList<Fan>>) {
        val result = externalGetFan()

        for (i in 0..(result.size - 1) / 3) {
            fans.value.add(
                Fan(
                    libIndex = result[i * 3].toInt(),
                    id = result[(i * 3) + 1],
                    libName = result[(i * 3) + 2],
                    globalType = FlagGlobalItemType.FAN_SENSOR,
                    specifyType = FlagSpecifyItemType.FAN_SENSOR,
                    name = result[(i * 3) + 2],
                )
            )
        }
    }

    override fun getTemp(temps: MutableStateFlow<SnapshotStateList<Temp>>) {
        val result = externalGetTemp()

        for (i in 0..(result.size - 1) / 3) {
            temps.value.add(
                Temp(
                    libIndex = result[i * 3].toInt(),
                    id = result[(i * 3) + 1],
                    libName = result[(i * 3) + 2],
                    globalType = FlagGlobalItemType.TEMP_SENSOR,
                    specifyType = FlagSpecifyItemType.TEMP_SENSOR,
                    name = result[(i * 3) + 2],
                )
            )
        }
    }

    override fun getControl(controls: MutableStateFlow<SnapshotStateList<Control>>) {
        val result = externalGetControl()

        for (i in 0..(result.size - 1) / 3) {
            controls.value.add(
                Control(
                    libIndex = result[i * 3].toInt(),
                    id = result[(i * 3) + 1],
                    libName = result[(i * 3) + 2],
                    globalType = FlagGlobalItemType.FAN_CONTROL,
                    specifyType = FlagSpecifyItemType.FAN_CONTROL,
                    name = result[(i * 3) + 2],
                )
            )
        }
    }

    override fun updateFan(
        fans: MutableStateFlow<SnapshotStateList<Fan>>,
        fans2: MutableStateFlow<SnapshotStateList<Fan>>
    ) {
        externalUpdateFan()

        for (i in fans.value.indices) {
            fans.update {
                fans.value[i] = fans.value[i].copy(
                    value = values[fans.value[i].libIndex]
                )
                it
            }
        }

        for (i in fans2.value.indices) {
            fans2.update {
                fans2.value[i] = fans2.value[i].copy(
                    value = values[fans2.value[i].libIndex]
                )
                it
            }
        }
    }

    override fun updateTemp(
        temps: MutableStateFlow<SnapshotStateList<Temp>>,
        temps2: MutableStateFlow<SnapshotStateList<Temp>>
    ) {
        externalUpdateTemp()

        for (i in temps.value.indices) {
            temps.update {
                temps.value[i] = temps.value[i].copy(
                    value = values[temps.value[i].libIndex]
                )
                it
            }
        }

        for (i in temps2.value.indices) {
            temps2.update {
                temps2.value[i] = temps2.value[i].copy(
                    value = values[temps2.value[i].libIndex]
                )
                it
            }
        }
    }

    override fun updateControl(
        controls: MutableStateFlow<SnapshotStateList<Control>>,
        controls2: MutableStateFlow<SnapshotStateList<Control>>
    ) {
        externalUpdateControl()


        for (i in controls.value.indices) {
            controls.update {
                controls.value[i] = controls.value[i].copy(
                    value = values[controls.value[i].libIndex]
                )
                it
            }
        }
        for (i in controls2.value.indices) {
            controls2.update {
                controls2.value[i] = controls2.value[i].copy(
                    value = values[controls2.value[i].libIndex]
                )
                it
            }
        }
    }


    override fun setControl(index: Int, isAuto: Boolean, value: Int) {
        externalSetControl(index, isAuto, value)
    }

    private external fun externalStart(values: IntArray)
    private external fun externalStop()
    private external fun externalGetFan(): Array<String>
    private external fun externalGetTemp(): Array<String>
    private external fun externalGetControl(): Array<String>
    private external fun externalUpdateFan()
    private external fun externalUpdateTemp()
    private external fun externalUpdateControl()
    private external fun externalSetControl(index: Int, isAuto: Boolean, value: Int)
}