package ui.screen.itemsList.behaviorList

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import model.ItemType
import ui.screen.itemsList.behaviorList.flat.flatAddItem
import ui.screen.itemsList.behaviorList.flat.flatBody
import ui.screen.itemsList.behaviorList.linearAndTarget.linear.linearAddItem
import ui.screen.itemsList.behaviorList.linearAndTarget.linear.linearBody
import ui.screen.itemsList.behaviorList.linearAndTarget.target.targetAddItem
import ui.screen.itemsList.behaviorList.linearAndTarget.target.targetBody


private val bodyViewModel: BaseBehaviorVM = BaseBehaviorVM()

fun LazyListScope.behaviorAddItemList() {
    item { flatAddItem() }
    item { linearAddItem() }
    item { targetAddItem() }
}


fun LazyListScope.behaviorBodyList() {
    itemsIndexed(bodyViewModel.iBehaviors) { index, behavior ->

        when (behavior.type) {
            ItemType.BehaviorType.I_B_FLAT -> {
                flatBody(
                    behavior = behavior,
                    index = index
                )
            }

            ItemType.BehaviorType.I_B_LINEAR -> {
                linearBody(
                    behavior = behavior,
                    index = index
                )
            }

            ItemType.BehaviorType.I_B_TARGET -> {
                targetBody(
                    behavior = behavior,
                    index = index
                )
            }

            else -> throw Exception("unspecified item type")
        }

    }
}