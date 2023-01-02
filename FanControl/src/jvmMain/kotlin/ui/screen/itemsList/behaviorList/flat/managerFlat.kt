package ui.screen.itemsList.behaviorList.flat


import androidx.compose.runtime.Composable
import model.ItemType
import model.item.behavior.BehaviorItem
import model.item.behavior.FlatBehavior
import ui.screen.itemsList.baseItemAddItem
import ui.screen.itemsList.baseItemBody
import ui.utils.Resources

private val viewModel: FlatVM = FlatVM()

@Composable
fun flatBody(
    behavior: BehaviorItem,
    index: Int,
    onEditClick: () -> Unit,
    onNameChange: (String) -> Unit,
) {
    val flatBehavior = behavior.extension as FlatBehavior

    baseItemBody(
        iconPainter = Resources.getIcon("horizontal_rule"),
        iconContentDescription = Resources.getString("ct/flat"),
        onNameChange = onNameChange,
        onEditClick = onEditClick,
        item = behavior
    ) {
        baseFlat(
            value = flatBehavior.value,
            enabled = true,
            onLess = { viewModel.onLess(index, flatBehavior.value) },
            onMore = { viewModel.onMore(index, flatBehavior.value) }
        ) { viewModel.onValueChange(index, it.toInt()) }
    }
}


@Composable
fun flatAddItem(
    onEditClick: () -> Unit
) {
    baseItemAddItem(
        iconPainter = Resources.getIcon("horizontal_rule"),
        iconContentDescription = Resources.getString("ct/flat"),
        name = Resources.getString("add_item/flat_name"),
        onEditClick = onEditClick,
        type = ItemType.BehaviorType.I_B_FLAT
    ) {
        baseFlat(
            value = 50,
            enabled = false
        )
    }
}