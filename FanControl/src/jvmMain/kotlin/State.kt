import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.hardware.Control
import model.hardware.Sensor
import model.item.BehaviorItem
import model.item.ControlItem
import model.item.SensorItem

class State {

    companion object {
        val _fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = MutableStateFlow(mutableStateListOf())
        val _tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = MutableStateFlow(mutableStateListOf())
        val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = MutableStateFlow(mutableStateListOf())
        val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> =
            MutableStateFlow(mutableStateListOf())

        val _fanList: MutableStateFlow<SnapshotStateList<Sensor>> = MutableStateFlow(mutableStateListOf())
        val _tempList: MutableStateFlow<SnapshotStateList<Sensor>> = MutableStateFlow(mutableStateListOf())
        val _controlList: MutableStateFlow<SnapshotStateList<Control>> = MutableStateFlow(mutableStateListOf())


        @OptIn(ExperimentalMaterial3Api::class)
        val _drawerState: MutableStateFlow<DrawerState> = MutableStateFlow(DrawerState(DrawerValue.Closed))
        val _addItemExpanded: MutableStateFlow<MutableState<Boolean>> = MutableStateFlow(mutableStateOf(false))

        val _editModeActivated: MutableStateFlow<MutableState<Boolean>> = MutableStateFlow(mutableStateOf(false))
    }
}


enum class Source {
    ADD,
    BODY
}