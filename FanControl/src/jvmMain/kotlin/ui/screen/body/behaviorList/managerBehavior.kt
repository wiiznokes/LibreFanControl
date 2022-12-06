package ui.screen.body.behaviorList

import Source
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import model.item.Behavior
import ui.component.baseBehavior
import ui.utils.Resources


private val viewModel: BehaviorViewModel = BehaviorViewModel()


fun LazyListScope.behaviorList(
    editModeActivated: Boolean
) {
    itemsIndexed(viewModel.behaviorItemList.value) { index, behavior ->
        behavior(
            behavior = behavior,
            index = index,
            editModeActivated = editModeActivated
        )
    }
}


@Composable
fun behavior(
    behavior: Behavior,
    index: Int,
    editModeActivated: Boolean
) {

    val viewModel = BehaviorViewModel()

    baseBehavior(
        iconPainter = Resources.getIcon("horizontal_rule"),
        iconContentDescription = "",
        name = behavior.name,
        label = "name",
        onNameChange = { viewModel.setName(it, index) },
        editModeActivated = editModeActivated,
        onEditClick = { viewModel.remove(index) },
        source = Source.BODY,

        onMoreButtonClick = {
            viewModel.onMore(index)
        },
        onLessButtonClick = {
            viewModel.onLess(index)
        },
        value = behavior.value,
        onSliderValueChange = {
            viewModel.onChange(index, it)
        }
    )
}



