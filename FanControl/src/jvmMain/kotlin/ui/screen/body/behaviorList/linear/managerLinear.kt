package ui.screen.body.behaviorList.linear

import androidx.compose.runtime.Composable
import model.item.behavior.BehaviorItem
import ui.component.baseItemBody
import ui.utils.Resources

private val viewModel: LinearBehaviorViewModel = LinearBehaviorViewModel()

@Composable
fun linearBihavior(
    behavior: BehaviorItem,
    index: Int,
    onEditClick: () -> Unit,
    onNameChange: (String) -> Unit,
    editModeActivated: Boolean,
) {
    baseItemBody(
        iconPainter = Resources.getIcon("linear"),
        iconContentDescription = Resources.getString("behavior_icon_content_description"),
        name = behavior.name,
        onNameChange = onNameChange,
        editModeActivated = editModeActivated,
        onEditClick = onEditClick
    ) {

    }
}