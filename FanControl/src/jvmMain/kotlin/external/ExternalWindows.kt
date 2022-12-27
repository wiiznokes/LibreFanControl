package external

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.ItemType
import model.hardware.Sensor
import model.item.ControlItem
import utils.getAvailableId

class ExternalWindows : External {


    private val values: IntArray = IntArray(25) { 0 }
    override fun start(
        fans: MutableStateFlow<SnapshotStateList<Sensor>>,
        temps: MutableStateFlow<SnapshotStateList<Sensor>>,
        controls: MutableStateFlow<SnapshotStateList<ControlItem>>
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

    override fun getFan(fans: MutableStateFlow<SnapshotStateList<Sensor>>) {
        val result = externalGetFan()

        for (i in 0..(result.size - 1) / 3) {
            fans.value.add(
                Sensor(
                    libIndex = result[i * 3].toInt(),
                    libId = result[(i * 3) + 1],
                    libName = result[(i * 3) + 2],
                    type = ItemType.SensorType.FAN,
                )
            )
        }
    }

    override fun getTemp(temps: MutableStateFlow<SnapshotStateList<Sensor>>) {
        val result = externalGetTemp()

        for (i in 0..(result.size - 1) / 3) {
            temps.value.add(
                Sensor(
                    libIndex = result[i * 3].toInt(),
                    libId = result[(i * 3) + 1],
                    libName = result[(i * 3) + 2],
                    type = ItemType.SensorType.TEMP,
                )
            )
        }
    }

    override fun getControl(controls: MutableStateFlow<SnapshotStateList<ControlItem>>) {
        val result = externalGetControl()

        for (i in 0..(result.size - 1) / 3) {
            controls.value.add(
                ControlItem(
                    libIndex = result[i * 3].toInt(),
                    libId = result[(i * 3) + 1],
                    libName = result[(i * 3) + 2],
                    name = result[(i * 3) + 2],

                    itemId = getAvailableId(
                        controls.value.map { item ->
                            item.itemId
                        }
                    ),
                )
            )
        }
    }

    override fun updateFan(fans: MutableStateFlow<SnapshotStateList<Sensor>>) {
        externalUpdateFan()

        for (i in fans.value.indices) {
            fans.update {
                fans.value[i] = fans.value[i].copy(
                    value = values[fans.value[i].libIndex]
                )
                it
            }
        }
    }

    override fun updateTemp(temps: MutableStateFlow<SnapshotStateList<Sensor>>) {
        externalUpdateTemp()

        for (i in temps.value.indices) {
            temps.update {
                temps.value[i] = temps.value[i].copy(
                    value = values[temps.value[i].libIndex]
                )
                it
            }
        }
    }

    override fun updateControl(controls: MutableStateFlow<SnapshotStateList<ControlItem>>) {
        externalUpdateControl()


        for (i in controls.value.indices) {
            controls.update {
                controls.value[i] = controls.value[i].copy(
                    value = values[controls.value[i].libIndex]
                )
                it
            }
        }
    }


    override fun setControl(libIndex: Int, isAuto: Boolean, value: Int?) {
        // case is auto == true
        if (value == null)
            externalSetControl(libIndex, isAuto, 100)
        else
            externalSetControl(libIndex, isAuto, value)
    }

    private external fun externalStart(values: IntArray)
    private external fun externalStop()
    private external fun externalGetFan(): Array<String>
    private external fun externalGetTemp(): Array<String>
    private external fun externalGetControl(): Array<String>
    private external fun externalUpdateFan()
    private external fun externalUpdateTemp()
    private external fun externalUpdateControl()
    private external fun externalSetControl(libIndex: Int, isAuto: Boolean, value: Int)
}