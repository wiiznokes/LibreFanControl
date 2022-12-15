package ui.screen.addItem.behavior

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.ItemType
import model.item.behavior.BehaviorItem
import model.item.behavior.FlatBehavior
import model.item.behavior.LinearBehavior
import ui.utils.Resources
import ui.utils.getAvailableId
import ui.utils.getAvailableName

class AddBehaviorViewModel(
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList
) {

    fun addFlat() {
        _behaviorItemList.update {
            _behaviorItemList.value.add(
                BehaviorItem(
                    name = getAvailableName(
                        list = _behaviorItemList.value,
                        prefix = Resources.getString("default/flat_name")
                    ),
                    type = ItemType.BehaviorType.FLAT,
                    flatBehavior = FlatBehavior(),
                    id = getAvailableId(
                        list = _behaviorItemList.value
                    )
                )
            )
            it
        }
    }

    fun addLinear() {
        val name = getAvailableName(
            list = _behaviorItemList.value,
            prefix = Resources.getString("default/linear_name")
        )

        _behaviorItemList.update {
            _behaviorItemList.value.add(
                BehaviorItem(
                    name = name,
                    type = ItemType.BehaviorType.LINEAR,
                    linearBehavior = LinearBehavior(),
                    id = getAvailableId(
                        list = _behaviorItemList.value
                    )
                )
            )
            it
        }
    }

}