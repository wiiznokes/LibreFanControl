package ui.screen.addItem.behavior

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.item.behavior.BehaviorItem
import model.item.behavior.FlatBehavior
import model.item.behavior.LinearBehavior
import ui.utils.Resources
import utils.getAvailableId
import utils.getAvailableName

class AddBehaviorViewModel(
    private val behaviorItemList: SnapshotStateList<BehaviorItem> = State.behaviorItemList
) {

    fun addFlat() {
        behaviorItemList.add(
            BehaviorItem(
                name = getAvailableName(
                    names = behaviorItemList.map { item ->
                        item.name
                    },
                    prefix = Resources.getString("default/flat_name")
                ),
                type = ItemType.BehaviorType.I_B_FLAT,
                extension = FlatBehavior(),
                itemId = getAvailableId(
                    ids = behaviorItemList.map { item ->
                        item.itemId
                    }
                )
            )
        )
    }

    fun addLinear() {
        behaviorItemList.add(
            BehaviorItem(
                name = getAvailableName(
                    names = behaviorItemList.map { item ->
                        item.name
                    },
                    prefix = Resources.getString("default/linear_name")
                ),
                type = ItemType.BehaviorType.I_B_LINEAR,
                extension = LinearBehavior(),
                itemId = getAvailableId(
                    ids = behaviorItemList.map { item ->
                        item.itemId
                    }
                )
            )
        )
    }

}