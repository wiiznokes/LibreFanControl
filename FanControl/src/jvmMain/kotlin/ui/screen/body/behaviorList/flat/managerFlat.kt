package ui.screen.body.behaviorList.flat


import androidx.compose.runtime.Composable
import model.item.behavior.BehaviorItem
import ui.component.baseBehavior
import ui.screen.body.behaviorList.BehaviorViewModel
import ui.utils.Resources


private val viewModel: FlatBehaviorViewModel = FlatBehaviorViewModel()

@Composable
fun flatBehavior(
    behaviorItem: BehaviorItem,
    index: Int,
    editModeActivated: Boolean,
    onNameChange: (String) -> Unit,
    onEditClick: () -> Unit,
) {
    baseBehavior(
        iconPainter = Resources.getIcon("horizontal_rule"),
        iconContentDescription = Resources.getString("behavior_icon_content_description"),
        name = behaviorItem.name,
        onNameChange = onNameChange,
        editModeActivated = editModeActivated,
        onEditClick = onEditClick,

        onMoreButtonClick = {
            viewModel.onMore(index, it)
        },
        onLessButtonClick = {
            viewModel.onLess(index, it)
        },
        value = behaviorItem.flatBehavior!!.value,
        onSliderValueChange = {
            viewModel.onChange(index, it)
        }
    )
}



