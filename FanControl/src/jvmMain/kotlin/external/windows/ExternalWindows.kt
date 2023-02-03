package external.windows

import androidx.compose.runtime.snapshots.SnapshotStateList
import external.External
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import model.HardwareType
import model.hardware.Control
import model.hardware.Sensor
import utils.Id.Companion.getAvailableId
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket




class ExternalWindows : External {

    private lateinit var process: Process

    private lateinit var client: Socket
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null

    private val byteArray = ByteArray(1024)




    private fun makeRequest(command: Command) {
        println("make request: $command")
        outputStream?.write(command.name.toByteArray())
        println("request send")
    }


    override fun start(
        fans: SnapshotStateList<Sensor>,
        temps: SnapshotStateList<Sensor>,
        controls: SnapshotStateList<Control>
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

        super.start(fans, temps, controls)
    }

    override fun stop() {
        makeRequest(Command.Stop)
        client.close()
    }

    override fun setControls(controls: SnapshotStateList<Control>) {
        val bytesRead = inputStream?.read(byteArray)
        val deviceList = ProtoHelper.getDeviceList(byteArray, bytesRead!!)

        deviceList.deviceList.forEach {
            controls.add(
                Control(
                    libIndex = it.index,
                    libId = it.id,
                    libName = it.name,
                    id = getAvailableId(controls.map { control -> control.id })
                )
            )
        }

    }

    override fun setFans(fans: SnapshotStateList<Sensor>) {
        val bytesRead = inputStream?.read(byteArray)
        val deviceList = ProtoHelper.getDeviceList(byteArray, bytesRead!!)

        deviceList.deviceList.forEach {
            fans.add(
                Sensor(
                    libIndex = it.index,
                    libId = it.id,
                    libName = it.name,
                    type = HardwareType.SensorType.H_S_FAN,
                    id = getAvailableId(fans.map { fan -> fan.id })
                )
            )
        }
    }

    override fun setTemps(temps: SnapshotStateList<Sensor>) {
        println("setControlList")
        val bytesRead = inputStream?.read(byteArray)
        val deviceList = ProtoHelper.getDeviceList(byteArray, bytesRead!!)

        deviceList.deviceList.forEach {
            temps.add(
                Sensor(
                    libIndex = it.index,
                    libId = it.id,
                    libName = it.name,
                    type = HardwareType.SensorType.H_S_TEMP,
                    id = getAvailableId(temps.map { temp-> temp.id })
                )
            )
        }
    }

    override fun setUpdateControls(controls: SnapshotStateList<Control>) {
        println("updateControlList")
        makeRequest(Command.Controls)

        val bytesRead = inputStream?.read(byteArray)
        val updateList = ProtoHelper.getUpdateList(byteArray, bytesRead!!)

        updateList.forEach {
            controls[it.index] = controls[it.index].copy(
                value = it.value
            )
        }
    }

    override fun setUpdateFans(fans: SnapshotStateList<Sensor>) {

    }

    override fun setUpdateTemps(temps: SnapshotStateList<Sensor>) {

    }

    override fun setControl(libIndex: Int, isAuto: Boolean, value: Int?) {
        val setter = ProtoHelper.getSetControl(libIndex, isAuto, value.let { 0 })

        outputStream?.write(setter)
        outputStream?.flush()
    }
}