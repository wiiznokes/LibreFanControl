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


private val bodyViewModel: BodyBehaviorVM = BodyBehaviorVM()
private val addViewModel = AddBehaviorVM()

fun LazyListScope.behaviorAddItemList() {
    item { flatAddItem(onEditClick = { addViewModel.addFlat() }) }
    item { linearAddItem(onEditClick = { addViewModel.addLinear() }) }
    item { targetAddItem(onEditClick = { addViewModel.addTarget() }) }
}


fun LazyListScope.behaviorBodyList() {
    itemsIndexed(bodyViewModel.behaviorList) { index, behavior ->

        when (behavior.type) {
            ItemType.BehaviorType.I_B_FLAT -> {
                flatBody(
                    behavior = behavior,
                    index = index,
                    onNameChange = { bodyViewModel.setName(it, index) },
                    onEditClick = { bodyViewModel.remove(index) },
                )
            }

            ItemType.BehaviorType.I_B_LINEAR -> {
                linearBody(
                    behavior = behavior,
                    index = index,
                    onNameChange = { bodyViewModel.setName(it, index) },
                    onEditClick = { bodyViewModel.remove(index) },
                )
            }

            ItemType.BehaviorType.I_B_TARGET -> {
                targetBody(
                    behavior = behavior,
                    index = index,
                    onNameChange = { bodyViewModel.setName(it, index) },
                    onEditClick = { bodyViewModel.remove(index) },
                )
            }

            else -> throw Exception("unspecified item type")
        }

    }
}