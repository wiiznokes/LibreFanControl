package ui.screen.body.behaviorList

import Source
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import model.item.BehaviorItem
import ui.component.baseBehavior
import ui.utils.Resources


private val viewModel: BehaviorViewModel = BehaviorViewModel()


fun LazyListScope.behaviorList(
    editModeActivated: Boolean
) {
    itemsIndexed(viewModel.behaviorItemList.value) { index, behavior ->
        behavior(
            behaviorItem = behavior,
            index = index,
            editModeActivated = editModeActivated
        )
    }
}


@Composable
fun behavior(
    behaviorItem: BehaviorItem,
    index: Int,
    editModeActivated: Boolean
) {
    baseBehavior(
        iconPainter = Resources.getIcon("horizontal_rule"),
        iconContentDescription = "",
        name = behaviorItem.name,
        label = "name",
        onNameChange = { viewModel.setName(it, index) },
        editModeActivated = editModeActivated,
        onEditClick = { viewModel.remove(index) },
        source = Source.BODY,

        onMoreButtonClick = {
            viewModel.onMore(index, it)
        },
        onLessButtonClick = {
            viewModel.onLess(index, it)
        },
        value = behaviorItem.value,
        onSliderValueChange = {
            viewModel.onChange(index, it)
        }
    )
}



