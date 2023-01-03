package ui.screen.itemsList.behaviorList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.item.behavior.Behavior
import model.item.behavior.Flat
import model.item.behavior.Linear
import ui.utils.Resources
import utils.getAvailableId
import utils.getAvailableName

class AddBehaviorVM(
    private val behaviorList: SnapshotStateList<Behavior> = State.behaviorList
) {

    fun addFlat() {
        behaviorList.add(
            Behavior(
                name = getAvailableName(
                    names = behaviorList.map { item ->
                        item.name
                    },
                    prefix = Resources.getString("default/flat_name")
                ),
                type = ItemType.BehaviorType.I_B_FLAT,
                extension = Flat(),
                itemId = getAvailableId(
                    ids = behaviorList.map { item ->
                        item.itemId
                    }
                )
            )
        )
    }

    fun addLinear() {
        behaviorList.add(
            Behavior(
                name = getAvailableName(
                    names = behaviorList.map { item ->
                        item.name
                    },
                    prefix = Resources.getString("default/linear_name")
                ),
                type = ItemType.BehaviorType.I_B_LINEAR,
                extension = Linear(),
                itemId = getAvailableId(
                    ids = behaviorList.map { item ->
                        item.itemId
                    }
                )
            )
        )
    }

}