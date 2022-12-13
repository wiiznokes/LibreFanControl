package ui.screen.addItem

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.ItemType
import model.hardware.Control
import model.item.BehaviorItem
import model.item.ControlItem

class AddItemViewModel(
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList,
    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
    private val _controlList: MutableStateFlow<SnapshotStateList<Control>> = State._controlList,
) {

    val controlList = _controlList.asStateFlow()
    val controlItemList = _controlItemList.asStateFlow()


    fun addBehavior() {
        _behaviorItemList.update {
            it.add(
                BehaviorItem(
                    name = "Behavior",
                    type = ItemType.BEHAVIOR
                )
            )
            it
        }
    }

    fun addControl() {
        _behaviorItemList.update {
            it.add(
                ControlItem(
                    name = "Control",
                    type = ItemType.CONTROL
                )
            )
            it
        }
    }
}


