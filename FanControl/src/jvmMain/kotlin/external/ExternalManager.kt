package external

import State.hControls
import State.hSensorsList
import androidx.compose.runtime.snapshots.SnapshotStateList
import external.linux.ExternalLinux
import external.windows.ExternalWindows
import model.hardware.Control
import model.hardware.Sensor


/**
 * This class is used to fetch sensor value, and set fan speed.
 * It provides an abstraction for handling Linux and Windows
 * with the External interface
 */
object ExternalManager : External {

    private val external: External = when (OS.linux) {
        OS.windows -> ExternalWindows()
        OS.linux -> ExternalLinux()
        OS.unsupported -> throw Exception("unsupported OS")
    }

    override fun start(
        fans: SnapshotStateList<Sensor>,
        temps: SnapshotStateList<Sensor>,
        controls: SnapshotStateList<Control>
    ) {
        external.start(hSensorsList.hFans, hSensorsList.hTemps, hControls)

        setControls(controls)
        setFans(fans)
        setTemps(temps)

        println("start lib : success")
    }

    override fun close() {
        external.close()
        println("stop lib : success")
    }

    override fun setControls(controls: SnapshotStateList<Control>) {
        external.setControls(controls)
    }

    override fun setFans(fans: SnapshotStateList<Sensor>) {
        external.setFans(fans)
    }

    override fun setTemps(temps: SnapshotStateList<Sensor>) {
        external.setTemps(temps)
    }

    override fun setUpdateControls(controls: SnapshotStateList<Control>) {
        external.setUpdateControls(hControls)
    }

    override fun setUpdateFans(fans: SnapshotStateList<Sensor>) {
        external.setUpdateFans(hSensorsList.hFans)
    }

    override fun setUpdateTemps(temps: SnapshotStateList<Sensor>) {
        external.setUpdateTemps(hSensorsList.hTemps)
    }

    override fun reloadSetting() {
        external.reloadSetting()
    }

    override fun reloadConfig(id: Long?) {
        external.reloadConfig(id)
    }

}