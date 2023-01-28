package external.windows

import androidx.compose.runtime.snapshots.SnapshotStateList
import external.External
import model.hardware.Sensor
import model.item.control.Control
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class ExternalWindows : External {


    override fun start(
        fanList: SnapshotStateList<Sensor>,
        tempList: SnapshotStateList<Sensor>,
        controlList: SnapshotStateList<Control>,
        controlChangeList: SnapshotStateList<Boolean>
    ) {

        val client = Socket("::1", 11000)
        val output = PrintWriter(client.getOutputStream(), true)
        val input = BufferedReader(InputStreamReader(client.inputStream))

        println("Client sending [Hello]")
        output.println("Hello\n")
        println("Client receiving [${input.readLine()}]")
        client.close()
    }

    override fun stop() {
    }

    override fun setFanList(fanList: SnapshotStateList<Sensor>) {

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