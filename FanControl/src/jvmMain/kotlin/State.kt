import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.Configuration
import model.hardware.Sensor
import model.item.ControlItem
import model.item.SensorItem
import model.item.behavior.BehaviorItem

class State {

    companion object {
        val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = MutableStateFlow(mutableStateListOf())
        val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> =
            MutableStateFlow(mutableStateListOf())
        val _fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = MutableStateFlow(mutableStateListOf())
        val _tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = MutableStateFlow(mutableStateListOf())

        val _fanList: MutableStateFlow<SnapshotStateList<Sensor>> = MutableStateFlow(mutableStateListOf())
        val _tempList: MutableStateFlow<SnapshotStateList<Sensor>> = MutableStateFlow(mutableStateListOf())

        val _addItemExpanded: MutableStateFlow<MutableState<Boolean>> = MutableStateFlow(mutableStateOf(false))
        val _editModeActivated: MutableStateFlow<MutableState<Boolean>> = MutableStateFlow(mutableStateOf(false))

        val _configList: MutableStateFlow<SnapshotStateList<Configuration>> = MutableStateFlow(mutableStateListOf())
        val _indexConfig: MutableStateFlow<MutableState<Int>> = MutableStateFlow(mutableStateOf(-1))
    }
}


enum class Source {
    ADD,
    BODY
}