package external.windows

import androidx.compose.runtime.snapshots.SnapshotStateList
import external.External
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import model.hardware.Sensor
import model.item.control.Control
import java.io.File
import java.io.InputStream
import java.net.Socket

class ExternalWindows : External {

    private lateinit var process: Process

    private lateinit var client: Socket
    private var inputStream: InputStream? = null

    private val byteArray = ByteArray(1024)


    override fun start(
        fanList: SnapshotStateList<Sensor>,
        tempList: SnapshotStateList<Sensor>,
        controlList: SnapshotStateList<Control>,
        controlChangeList: SnapshotStateList<Boolean>
    ) {
        val path = File(System.getProperty("compose.application.resources.dir"))
            .resolve("HardwareDaemon.exe").path

        process = ProcessBuilder(path).start()

        runBlocking {
            delay(2000L)
        }

        client = Socket("::1", 11000)
        inputStream = client.getInputStream()
        super.start(fanList, tempList, controlList, controlChangeList)
    }

    override fun stop() {
        client.close()
    }


    override fun setFanList(fanList: SnapshotStateList<Sensor>) {
        val bytesRead = inputStream?.read(byteArray)

        val deviceList = ProtoHelper.getDeviceList(byteArray, bytesRead!!)

        println(deviceList.type)
        deviceList.deviceList.forEach {
            println(it.name)
            println(it.value)
        }
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