import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.hardware.Control
import model.hardware.Sensor
import model.item.Behavior
import model.item.LibItem

class State {

    companion object {
        val _fanItemList: MutableStateFlow<SnapshotStateList<LibItem>> = MutableStateFlow(mutableStateListOf())
        val _tempItemList: MutableStateFlow<SnapshotStateList<LibItem>> = MutableStateFlow(mutableStateListOf())
        val _controlItemList: MutableStateFlow<SnapshotStateList<LibItem>> = MutableStateFlow(mutableStateListOf())
        val _behaviorItemList: MutableStateFlow<SnapshotStateList<Behavior>> = MutableStateFlow(mutableStateListOf())

        val _fanList: MutableStateFlow<SnapshotStateList<Sensor>> = MutableStateFlow(mutableStateListOf())
        val _tempList: MutableStateFlow<SnapshotStateList<Sensor>> = MutableStateFlow(mutableStateListOf())
        val _controlList: MutableStateFlow<SnapshotStateList<Control>> = MutableStateFlow(mutableStateListOf())


        @OptIn(ExperimentalMaterial3Api::class)
        val _drawerState: MutableStateFlow<DrawerState> = MutableStateFlow(DrawerState(DrawerValue.Closed))
        val _addItemExpanded: MutableStateFlow<Boolean> = MutableStateFlow(false)

        val _editModeActivated: MutableStateFlow<Boolean> = MutableStateFlow(false)
    }
}

class FlagGlobalItemType {
    companion object {
        // behavior
        const val FLAT_BEHAVIOR = "FLAT_BEHAVIOR"

        // control
        const val FAN_CONTROL = "FAN_CONTROL"

        // sensor
        const val FAN_SENSOR = "FAN_SENSOR"
        const val TEMP_SENSOR = "TEMP_SENSOR"
    }
}

class FlagSpecifyItemType {
    companion object {
        // behavior
        const val FLAT_BEHAVIOR = "FLAT_BEHAVIOR"

        // control
        const val FAN_CONTROL = "FAN_CONTROL"

        // sensor
        const val FAN_SENSOR = "FAN_SENSOR"
        const val TEMP_SENSOR = "TEMP_SENSOR"
    }
}

enum class Source {
    ADD,
    BODY
}