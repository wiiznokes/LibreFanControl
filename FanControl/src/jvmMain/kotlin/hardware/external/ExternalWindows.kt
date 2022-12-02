package hardware.external

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.control.Control
import model.hardware.sensor.Fan
import model.hardware.sensor.Temp
import ui.FlagGlobalItemType
import ui.FlagSpecifyItemType

class ExternalWindows : External {


    private val values: IntArray = IntArray(25) { 0 }
    override fun start() {
        try {
            System.loadLibrary("CppProxy")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        externalStart(values)
    }

    override fun stop() {
        externalStop()
    }

    override fun getFan(): SnapshotStateList<Fan> {
        val result = externalGetFan()
        val fans = mutableStateListOf<Fan>()

        for (i in 0..(result.size - 1) / 3) {
            fans.add(
                Fan(
                    index = result[i * 3].toInt(),
                    id = result[(i * 3) + 1],
                    libName = result[(i * 3) + 2],
                    globalType = FlagGlobalItemType.FAN_SENSOR,
                    specifyType = FlagSpecifyItemType.FAN_SENSOR,
                    name = result[(i * 3) + 2],
                )
            )
        }
        return fans
    }

    override fun getTemp(): SnapshotStateList<Temp> {
        val result = externalGetTemp()
        val temps = mutableStateListOf<Temp>()

        for (i in 0..(result.size - 1) / 3) {
            temps.add(
                Temp(
                    index = result[i * 3].toInt(),
                    id = result[(i * 3) + 1],
                    libName = result[(i * 3) + 2],
                    globalType = FlagGlobalItemType.TEMP_SENSOR,
                    specifyType = FlagSpecifyItemType.TEMP_SENSOR,
                    name = result[(i * 3) + 2],
                )
            )
        }
        return temps
    }

    override fun getControl(): SnapshotStateList<Control> {
        val result = externalGetControl()
        val controls = mutableStateListOf<Control>()

        for (i in 0..(result.size - 1) / 3) {
            controls.add(
                Control(
                    index = result[i * 3].toInt(),
                    id = result[(i * 3) + 1],
                    libName = result[(i * 3) + 2],
                    globalType = FlagGlobalItemType.FAN_CONTROL,
                    specifyType = FlagSpecifyItemType.FAN_CONTROL,
                    name = result[(i * 3) + 2],
                )
            )
        }
        return controls
    }

    override fun updateFan(fans: SnapshotStateList<Fan>) {
        externalUpdateFan(fans.size)

        for (i in fans.indices) {
            fans[i] = fans[i].copy(
                value = values[i]
            )
        }
    }

    override fun updateTemp(temps: SnapshotStateList<Temp>) {
        externalUpdateTemp(temps.size)
        for (i in temps.indices) {
            temps[i] = temps[i].copy(
                value = values[i]
            )
        }
    }

    override fun updateControl(controls: SnapshotStateList<Control>) {
        externalUpdateControl(controls.size)
        for (i in controls.indices) {
            controls[i] = controls[i].copy(
                value = values[i]
            )
        }
    }


    override fun setControl(id: String, isAuto: Boolean, value: Int) {
        externalSetControl(id, isAuto, value)
    }

    private external fun externalStart(values: IntArray)
    private external fun externalStop()
    private external fun externalGetFan(): Array<String>
    private external fun externalGetTemp(): Array<String>
    private external fun externalGetControl(): Array<String>
    private external fun externalUpdateFan(size: Int)
    private external fun externalUpdateTemp(size: Int)
    private external fun externalUpdateControl(size: Int)
    private external fun externalSetControl(id: String, isAuto: Boolean, value: Int)
}