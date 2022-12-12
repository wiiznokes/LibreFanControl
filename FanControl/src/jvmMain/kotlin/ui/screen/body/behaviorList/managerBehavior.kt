package ui.screen.body.behaviorList


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
        iconContentDescription = Resources.getString("behavior_icon_content_description"),
        name = behaviorItem.name,
        onNameChange = { viewModel.setName(it, index) },
        editModeActivated = editModeActivated,
        onEditClick = { viewModel.remove(index) },

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



