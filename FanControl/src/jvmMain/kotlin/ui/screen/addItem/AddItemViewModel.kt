package ui.screen.addItem

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.ItemType
import model.item.BehaviorItem
import model.item.ControlItem

class AddItemViewModel(
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList,
    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,

    ) {
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


