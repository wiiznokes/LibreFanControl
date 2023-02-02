package external.windows

import androidx.compose.runtime.snapshots.SnapshotStateList
import external.External
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import model.HardwareType
import model.hardware.Sensor
import model.item.control.Control
import utils.Id.Companion.getAvailableId
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintWriter
import java.net.Socket






class ExternalWindows : External {
    enum class Command {
        GetInfo,
        Controls,
        Fans,
        Temps,
        Stop
    }

    private fun makeRequest(command: Command) {
        outputStream?.let { PrintWriter(it, true).write(command.name) }
        outputStream?.flush()
    }


    private lateinit var process: Process

    private lateinit var client: Socket
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null

    private val byteArray = ByteArray(1024)


    override fun start(
        fanList: SnapshotStateList<Sensor>,
        tempList: SnapshotStateList<Sensor>,
        controlList: SnapshotStateList<Control>,
        controlChangeList: SnapshotStateList<Boolean>
    ) {

        try {
            client = Socket("::1", 11000)
        } catch (e: Exception) {
            println("launch new process")
            val path = File(System.getProperty("compose.application.resources.dir"))
                .resolve("HardwareDaemon.exe").path

            process = ProcessBuilder(path).start()
            runBlocking {
                delay(2000L)
            }

            client = Socket("::1", 11000)
        }
        println("connected")

        makeRequest(Command.GetInfo)

        inputStream = client.getInputStream()
        outputStream = client.getOutputStream()

        super.start(fanList, tempList, controlList, controlChangeList)
    }

    override fun stop() {
        client.close()
    }

    override fun setControlList(controlList: SnapshotStateList<Control>) {

        val bytesRead = inputStream?.read(byteArray)
        val deviceList = ProtoHelper.getDeviceList(byteArray, bytesRead!!)

        deviceList.deviceList.forEach {
            controlList.add(
                Control(
                    libIndex = it.index,
                    libId = it.id,
                    libName = it.name,
                    name = it.name,
                    id = getAvailableId(
                        controlList.map { control -> control.id }
                    )
                )
            )
        }

    }

    override fun setFanList(fanList: SnapshotStateList<Sensor>) {
        val bytesRead = inputStream?.read(byteArray)
        val deviceList = ProtoHelper.getDeviceList(byteArray, bytesRead!!)

        deviceList.deviceList.forEach {
            fanList.add(
                Sensor(
                    libIndex = it.index,
                    libId = it.id,
                    libName = it.name,
                    type = HardwareType.SensorType.H_S_FAN,
                    id = getAvailableId(fanList.map { fan -> fan.id })
                )
            )
        }
    }

    override fun setTempList(tempList: SnapshotStateList<Sensor>) {
        val bytesRead = inputStream?.read(byteArray)
        val deviceList = ProtoHelper.getDeviceList(byteArray, bytesRead!!)

        deviceList.deviceList.forEach {
            tempList.add(
                Sensor(
                    libIndex = it.index,
                    libId = it.id,
                    libName = it.name,
                    type = HardwareType.SensorType.H_S_TEMP,
                    id = getAvailableId(tempList.map { temp-> temp.id })
                )
            )
        }
    }

    override fun updateControlList(controlList: SnapshotStateList<Control>) {
        makeRequest(Command.Controls)

        val bytesRead = inputStream?.read(byteArray)
        val updateList = ProtoHelper.getUpdateList(byteArray, bytesRead!!)

        updateList.forEach {
            controlList[it.index] = controlList[it.index].copy(
                value = it.value
            )
        }
    }

    override fun updateFanList(fanList: SnapshotStateList<Sensor>) {

    }

    override fun updateTempList(tempList: SnapshotStateList<Sensor>) {

    }

    override fun setControl(libIndex: Int, isAuto: Boolean, value: Int?) {
        val setter = ProtoHelper.getSetControl(libIndex, isAuto, value.let { 0 })

        outputStream?.write(setter)
        outputStream?.flush()
    }
}