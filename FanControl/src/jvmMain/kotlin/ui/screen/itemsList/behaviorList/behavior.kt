package ui.screen.itemsList.behaviorList

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import model.ItemType
import ui.screen.itemsList.behaviorList.flat.flatAddItem
import ui.screen.itemsList.behaviorList.flat.flatBody
import ui.screen.itemsList.behaviorList.linear.linearAddItem
import ui.screen.itemsList.behaviorList.linear.linearBody


private val bodyViewModel: BodyBehaviorVM = BodyBehaviorVM()
private val addViewModel = AddBehaviorVM()

fun LazyListScope.behaviorAddItemList() {
    item { flatAddItem(onEditClick = { addViewModel.addFlat() }) }
    item { linearAddItem(onEditClick = { addViewModel.addLinear() }) }
}


fun LazyListScope.behaviorBodyList() {
    itemsIndexed(bodyViewModel.behaviorItemList) { index, behavior ->

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

            ItemType.BehaviorType.I_B_TARGET -> TODO()
            else -> throw Exception("unspecified item type")
        }

    }
}