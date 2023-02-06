package ui.screen.itemsList.behaviorList

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import model.item.IFlat
import model.item.ILinear
import model.item.ITarget
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

        when (behavior) {
            is IFlat -> {
                flatBody(
                    flat = behavior,
                    index = index
                )
            }

            is ILinear -> {
                linearBody(
                    linear = behavior,
                    index = index
                )
            }

            is ITarget -> {
                targetBody(
                    target = behavior,
                    index = index
                )
            }
        }
    }
}