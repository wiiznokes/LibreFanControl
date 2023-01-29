package external.windows

import androidx.compose.runtime.snapshots.SnapshotStateList
import external.External
import model.HardwareType
import model.hardware.Sensor
import model.item.control.Control
import utils.Id.Companion.getAvailableId
import java.io.InputStream
import java.net.Socket

class ExternalWindows : External {

    private lateinit var client: Socket
    private var inputStream: InputStream? = null

    private val byteArray = ByteArray(1024)
    private var device: Data.Device? = null

    override fun start(
        fanList: SnapshotStateList<Sensor>,
        tempList: SnapshotStateList<Sensor>,
        controlList: SnapshotStateList<Control>,
        controlChangeList: SnapshotStateList<Boolean>
    ) {

        client = Socket("::1", 11000)
        inputStream = client.getInputStream()
        super.start(fanList, tempList, controlList, controlChangeList)
    }

    override fun stop() {
        client.close()
    }

    override fun setFanList(fanList: SnapshotStateList<Sensor>) {
        val bytesRead = inputStream?.read(byteArray)
        if (bytesRead == null || bytesRead == 0)
            return

        device = Data.Device.parseFrom(byteArray.sliceArray(0 until bytesRead))

        fanList.add(
            Sensor(
                libIndex = device!!.index,
                libId = device!!.id,
                libName = device!!.name,
                type = HardwareType.SensorType.H_S_FAN,
                id = getAvailableId(fanList.map { it.id })
            )
        )
    }

    override fun setTempList(tempList: SnapshotStateList<Sensor>) {

    }

    override fun setControlList(controlList: SnapshotStateList<Control>) {

    }

    override fun updateFanList(fanList: SnapshotStateList<Sensor>) {

    }

    override fun updateTempList(tempList: SnapshotStateList<Sensor>) {

    }

    override fun updateControlList(controlList: SnapshotStateList<Control>) {

    }

    override fun setControl(libIndex: Int, isAuto: Boolean, value: Int?) {

    }
}