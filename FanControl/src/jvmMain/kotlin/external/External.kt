package external

import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Sensor
import model.item.control.Control

interface External {

    fun start(
        fanList: SnapshotStateList<Sensor>,
        tempList: SnapshotStateList<Sensor>,
        controlList: SnapshotStateList<Control>,
        controlChangeList: SnapshotStateList<Boolean>
    ) {
        setFanList(fanList)
        setTempList(tempList)
        setControlList(controlList)
        /**
         * initialize controlChangeList with the same size of control
         * if a configuration is used, all value will be set to true
         */
        controlList.forEach { _ ->
            controlChangeList.add(false)
        }
    }

    fun stop()

    fun setFanList(fanList: SnapshotStateList<Sensor>)

    fun setTempList(tempList: SnapshotStateList<Sensor>)

    fun setControlList(controlList: SnapshotStateList<Control>)

    fun updateFanList(
        fanList: SnapshotStateList<Sensor>
    )

    fun updateTempList(
        tempList: SnapshotStateList<Sensor>
    )

    fun updateControlList(
        controlList: SnapshotStateList<Control>
    )

    fun setControl(libIndex: Int, isAuto: Boolean, value: Int?)
}