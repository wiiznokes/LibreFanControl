package ui.screen.itemsList.behaviorList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.item.behavior.Behavior
import model.item.behavior.Flat
import model.item.behavior.Linear
import model.item.behavior.Target
import ui.utils.Resources
import utils.Id.Companion.getAvailableId
import utils.Name.Companion.getAvailableName

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
                id = getAvailableId(
                    ids = behaviorList.map { item ->
                        item.id
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
                id = getAvailableId(
                    ids = behaviorList.map { item ->
                        item.id
                    }
                )
            )
        )
    }

    fun addTarget() {
        behaviorList.add(
            Behavior(
                name = getAvailableName(
                    names = behaviorList.map { item ->
                        item.name
                    },
                    prefix = Resources.getString("default/target_name")
                ),
                type = ItemType.BehaviorType.I_B_TARGET,
                extension = Target(),
                id = getAvailableId(
                    ids = behaviorList.map { item ->
                        item.id
                    }
                )
            )
        )
    }

}