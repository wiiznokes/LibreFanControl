package ui.screen.addItem.behavior

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.ItemType
import model.hardware.Control
import model.item.SensorItem
import model.item.behavior.BehaviorItem
import model.item.behavior.FlatBehavior
import model.item.behavior.LinearBehavior
import ui.utils.Resources

class AddBehaviorViewModel(
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList
) {

    fun addFlat() {
        _behaviorItemList.update {
            _behaviorItemList.value.add(
                BehaviorItem(
                    name = "behavior1",
                    type = ItemType.BehaviorType.FLAT,
                    flatBehavior = FlatBehavior()
                )
            )
            it
        }
    }

    fun addLinear() {
        _behaviorItemList.update {
            _behaviorItemList.value.add(
                BehaviorItem(
                    name = "behavior2",
                    type = ItemType.BehaviorType.LINEAR,
                    linearBehavior = LinearBehavior()
                )
            )
            it
        }
    }

}