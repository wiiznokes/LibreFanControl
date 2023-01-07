package external.windows

import androidx.compose.runtime.snapshots.SnapshotStateList
import external.External
import model.HardwareType
import model.hardware.Sensor
import model.item.control.Control
import utils.Id.Companion.getAvailableId

class ExternalWindows : External {


    private val values: IntArray = IntArray(25) { 0 }
    override fun start(
        fanList: SnapshotStateList<Sensor>,
        tempList: SnapshotStateList<Sensor>,
        controlList: SnapshotStateList<Control>
    ) {
        System.loadLibrary("CppProxy")
        externalStart(values)
        super.start(fanList, tempList, controlList)
    }

    override fun stop() {
        externalStop()
    }

    override fun setFanList(fanList: SnapshotStateList<Sensor>) {
        val result = externalGetFansInfo()

        for (i in 0..(result.size - 1) / 3) {
            fanList.add(
                Sensor(
                    libIndex = result[i * 3].toInt(),
                    libId = result[(i * 3) + 1],
                    libName = result[(i * 3) + 2],
                    type = HardwareType.SensorType.H_S_FAN,
                    id = getAvailableId(fanList.map { it.id })
                )
            )
        }
    }

    override fun setTempList(tempList: SnapshotStateList<Sensor>) {
        val result = externalGetTempsInfo()

        for (i in 0..(result.size - 1) / 3) {
            tempList.add(
                Sensor(
                    libIndex = result[i * 3].toInt(),
                    libId = result[(i * 3) + 1],
                    libName = result[(i * 3) + 2],
                    type = HardwareType.SensorType.H_S_TEMP,
                    id = getAvailableId(tempList.map { it.id })
                )
            )
        }
    }

    override fun setControlList(controlList: SnapshotStateList<Control>) {
        val result = externalGetControlsInfo()

        for (i in 0..(result.size - 1) / 3) {

            controlList.add(
                Control(
                    libIndex = result[i * 3].toInt(),
                    libId = result[(i * 3) + 1],
                    libName = result[(i * 3) + 2],
                    name = result[(i * 3) + 2],
                    id = getAvailableId(
                        controlList.map { it.id }
                    )
                )
            )

        }
    }

    override fun updateFanList(fanList: SnapshotStateList<Sensor>) {
        externalUpdateFanList()

        for (i in fanList.indices) {
            fanList[i] = fanList[i].copy(
                value = values[fanList[i].libIndex]
            )
        }
    }

    override fun updateTempList(tempList: SnapshotStateList<Sensor>) {
        externalUpdateTempList()
        for (i in tempList.indices) {
            tempList[i] = tempList[i].copy(
                value = values[tempList[i].libIndex]
            )
        }
    }

    override fun updateControlList(controlList: SnapshotStateList<Control>) {
        externalUpdateControlList()

        for (i in controlList.indices) {
            controlList[i] = controlList[i].copy(
                value = values[controlList[i].libIndex]
            )
        }
    }

    override fun setControl(libIndex: Int, isAuto: Boolean, value: Int?) {
        externalSetControl(
            libIndex = libIndex,
            isAuto = isAuto,
            value = value ?: 100
        )
    }

    private external fun externalStart(values: IntArray)
    private external fun externalStop()
    private external fun externalGetFansInfo(): Array<String>
    private external fun externalGetTempsInfo(): Array<String>
    private external fun externalGetControlsInfo(): Array<String>
    private external fun externalUpdateFanList()
    private external fun externalUpdateTempList()
    private external fun externalUpdateControlList()
    private external fun externalSetControl(libIndex: Int, isAuto: Boolean, value: Int)
}