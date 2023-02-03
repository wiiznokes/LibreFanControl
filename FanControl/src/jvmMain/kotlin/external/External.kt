package external

import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Control
import model.hardware.Sensor





interface External {

    enum class Command {
        Close,
        GetControlsInfo,
        GetFansInfo,
        GetTempsInfo,

        GetUpdateControls,
        GetUpdateFans,
        GetUpdateTemps,

        ReloadSetting,
        ReloadConfig
    }

    fun start(
        fans: SnapshotStateList<Sensor>,
        temps: SnapshotStateList<Sensor>,
        controls: SnapshotStateList<Control>
    ) {
        setControls(controls)
        setFans(fans)
        setTemps(temps)
    }
    fun close()

    fun setControls(controls: SnapshotStateList<Control>)
    fun setFans(fans: SnapshotStateList<Sensor>)
    fun setTemps(temps: SnapshotStateList<Sensor>)

    fun setUpdateControls(controls: SnapshotStateList<Control>)
    fun setUpdateFans(fans: SnapshotStateList<Sensor>)
    fun setUpdateTemps(temps: SnapshotStateList<Sensor>)

    fun reloadSetting()
    fun reloadConfig(id: Long?)
}