package ui.screen.itemsList.behaviorList.flat


import androidx.compose.runtime.Composable
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
) {
    val flat = behavior.extension as Flat

    baseItemBody(
        icon = Resources.getIcon("items/horizontal_rule24"),
        onNameChange = { viewModel.setName(it, index) },
        onEditClick = { viewModel.remove(index) },
        item = behavior
    ) {

        baseFlat(
            value = flat.value,
            onLess = { viewModel.onLess(index, flat.value) },
            onMore = { viewModel.onMore(index, flat.value) },
            onValueChange = { viewModel.onValueChange(index, it.toInt()) }
        )
    }
}


@Composable
fun flatAddItem() {
    baseItemAddItem(
        icon = Resources.getIcon("items/horizontal_rule24"),
        name = Resources.getString("add_item/name/flat"),
        onEditClick = { viewModel.addBehavior(viewModel.defaultFlat()) },
        text = Resources.getString("add_item/info/flat")
    )
}