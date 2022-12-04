package ui.screen.body.behaviorList

import Source
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.StateFlow
import model.behavior.Behavior
import ui.component.baseBehavior
import ui.utils.Resources


@Composable
fun behavior(
    behavior: Behavior,
    index: Int,
    editModeActivated: StateFlow<Boolean>
) {

    val viewModel = BehaviorViewModel()

    baseBehavior(
        imageVector = Resources.getIcon("add"),
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



