package ui.screen.body.behaviorList

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import model.ItemType
import ui.screen.body.behaviorList.flat.flatBehavior


private val viewModel: BehaviorViewModel = BehaviorViewModel()

fun LazyListScope.behaviorList(
    editModeActivated: Boolean
) {
    itemsIndexed(viewModel.behaviorItemList.value) { index, behavior ->

        when(behavior.type) {
            ItemType.BehaviorType.FLAT -> {
                flatBehavior(
                    behaviorItem = behavior,
                    index = index,
                    editModeActivated = editModeActivated,
                    onNameChange = {
                        viewModel.setName(it, index)
                    },
                    onEditClick = {
                        viewModel.remove(index)
                    }
                )
            }
            ItemType.BehaviorType.LINEAR -> TODO()
            ItemType.BehaviorType.TARGET -> TODO()
        }

    }
}