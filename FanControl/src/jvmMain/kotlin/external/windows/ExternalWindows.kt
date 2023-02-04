package external.windows

import State.hControls
import State.hFans
import State.hTemps
import external.External
import external.ProtoHelper
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


    private fun makeRequest(command: External.Command) {
        println("make request: $command")
        outputStream?.write(command.name.toByteArray())
        println("request send")
    }


    override fun start() {

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

        inputStream = client.getInputStream()
        outputStream = client.getOutputStream()
    }

    override fun reloadSetting() {
        makeRequest(External.Command.ReloadSetting)
    }

    override fun reloadConfig(id: Long?) {
        makeRequest(External.Command.ReloadConfig)
    }

    override fun close() {
        makeRequest(External.Command.Close)
        client.close()
    }

    override fun setControls() {
        makeRequest(External.Command.GetControlsInfo)

        val bytesRead = inputStream?.read(byteArray)
        val deviceList = ProtoHelper.getDeviceList(byteArray, bytesRead!!)



        deviceList.deviceList.forEach {
            hControls.add(
                Control(
                    name = it.name,
                    id = getAvailableId(hControls.map { control -> control.id })
                )
            )
        }

    }

    override fun setFans() {
        makeRequest(External.Command.GetFansInfo)

        val bytesRead = inputStream?.read(byteArray)
        val deviceList = ProtoHelper.getDeviceList(byteArray, bytesRead!!)

        deviceList.deviceList.forEach {
            hFans.add(
                Sensor(
                    name = it.name,
                    type = HardwareType.SensorType.H_S_FAN,
                    id = getAvailableId(hFans.map { fan -> fan.id })
                )
            )
        }
    }

    override fun setTemps() {
        makeRequest(External.Command.GetTempsInfo)

        val bytesRead = inputStream?.read(byteArray)
        val deviceList = ProtoHelper.getDeviceList(byteArray, bytesRead!!)

        deviceList.deviceList.forEach {
            hTemps.add(
                Sensor(
                    name = it.name,
                    type = HardwareType.SensorType.H_S_TEMP,
                    id = getAvailableId(hTemps.map { temp -> temp.id })
                )
            )
        }
    }

    override fun updateControls() {
        makeRequest(External.Command.GetUpdateControls)

        val bytesRead = inputStream?.read(byteArray)
        val updateList = ProtoHelper.getUpdateList(byteArray, bytesRead!!)

        updateList.forEach {
            hControls[it.index] = hControls[it.index].copy(
                value = it.value
            )
        }
    }

    override fun updateFans() {
        makeRequest(External.Command.GetUpdateFans)

        val bytesRead = inputStream?.read(byteArray)
        val updateList = ProtoHelper.getUpdateList(byteArray, bytesRead!!)

        updateList.forEach {
            hFans[it.index] = hFans[it.index].copy(
                value = it.value
            )
        }
    }

    override fun updateTemps() {
        makeRequest(External.Command.GetUpdateTemps)

        val bytesRead = inputStream?.read(byteArray)
        val updateList = ProtoHelper.getUpdateList(byteArray, bytesRead!!)

        updateList.forEach {
            hTemps[it.index] = hTemps[it.index].copy(
                value = it.value
            )
        }
    }
}