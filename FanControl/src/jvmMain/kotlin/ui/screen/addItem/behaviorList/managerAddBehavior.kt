package ui.screen.addItem.behaviorList

import Source
import androidx.compose.runtime.Composable
import model.behavior.Behavior
import ui.component.baseBehavior
import ui.utils.Resources


@Composable
fun addBehavior(
    behavior: Behavior,
    index: Int
) {

    val viewModel = AddBehaviorViewModel()

    baseBehavior(
        iconPainter = Resources.getIcon("horizontal_rule"),
        iconContentDescription = "",
        name = behavior.name,
        onEditClick = { viewModel.add(index) },
        source = Source.ADD,
    )
}



