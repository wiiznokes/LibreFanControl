package ui.screen.itemsList.behaviorList.flat


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import model.ItemType
import model.item.behavior.Behavior
import model.item.behavior.Flat
import ui.screen.itemsList.baseItemAddItem
import ui.screen.itemsList.baseItemBody
import ui.utils.Resources

private val viewModel: FlatVM = FlatVM()

@Composable
fun flatBody(
    behavior: Behavior,
    index: Int,
    onEditClick: () -> Unit,
    onNameChange: (String) -> Unit,
) {
    val flat = behavior.extension as Flat

    baseItemBody(
        iconPainter = Resources.getIcon("items/horizontal_rule"),
        iconContentDescription = Resources.getString("ct/flat"),
        onNameChange = onNameChange,
        onEditClick = onEditClick,
        item = behavior
    ) {
        baseFlat(
            value = flat.value,
            enabled = true,
            onLess = { viewModel.onLess(index, flat.value) },
            onMore = { viewModel.onMore(index, flat.value) },
            onValueChange = { viewModel.onValueChange(index, it.toInt()) },
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
fun flatAddItem(
    onEditClick: () -> Unit
) {
    baseItemAddItem(
        iconPainter = Resources.getIcon("items/horizontal_rule"),
        iconContentDescription = Resources.getString("ct/flat"),
        name = Resources.getString("add_item/flat_name"),
        onEditClick = onEditClick,
        type = ItemType.BehaviorType.I_B_FLAT
    ) {
        baseFlat(
            value = 50,
            enabled = false,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}