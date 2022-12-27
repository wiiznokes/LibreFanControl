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
import utils.getAvailableId
import utils.getAvailableName

class AddBehaviorViewModel(
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList
) {

    fun addFlat() {
        _behaviorItemList.update {
            it.add(
                BehaviorItem(
                    name = getAvailableName(
                        names = _behaviorItemList.value.map { item ->
                            item.name
                        },
                        prefix = Resources.getString("default/flat_name")
                    ),
                    type = ItemType.BehaviorType.FLAT,
                    flatBehavior = FlatBehavior(),
                    itemId = getAvailableId(
                        ids = _behaviorItemList.value.map { item ->
                            item.itemId
                        }
                    )
                )
            )
            it
        }
    }

    fun addLinear() {
        val name = getAvailableName(
            names = _behaviorItemList.value.map { item ->
                item.name
            },
            prefix = Resources.getString("default/linear_name")
        )

        _behaviorItemList.update {
            it.add(
                BehaviorItem(
                    name = name,
                    type = ItemType.BehaviorType.LINEAR,
                    linearBehavior = LinearBehavior(),
                    itemId = getAvailableId(
                        ids = _behaviorItemList.value.map { item ->
                            item.itemId
                        }
                    )
                )
            )
            it
        }
    }

}