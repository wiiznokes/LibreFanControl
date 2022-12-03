import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.behavior.Behavior
import model.hardware.control.Control
import model.hardware.sensor.Fan
import model.hardware.sensor.Temp

class State {

    companion object {
        val _fanList: MutableStateFlow<SnapshotStateList<Fan>> = MutableStateFlow(mutableStateListOf())
        val _tempList: MutableStateFlow<SnapshotStateList<Temp>> = MutableStateFlow(mutableStateListOf())
        val _controlList: MutableStateFlow<SnapshotStateList<Control>> = MutableStateFlow(mutableStateListOf())
        val _behaviorList: MutableStateFlow<SnapshotStateList<Behavior>> = MutableStateFlow(mutableStateListOf())

        val _addFanList: MutableStateFlow<SnapshotStateList<Fan>> = MutableStateFlow(mutableStateListOf())
        val _addTempList: MutableStateFlow<SnapshotStateList<Temp>> = MutableStateFlow(mutableStateListOf())
        val _addControlList: MutableStateFlow<SnapshotStateList<Control>> = MutableStateFlow(mutableStateListOf())
        val _addBehaviorList: MutableStateFlow<SnapshotStateList<Behavior>> = MutableStateFlow(mutableStateListOf())
    }
}