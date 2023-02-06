package ui.screen.itemsList.behaviorList.flat


import androidx.compose.runtime.Composable
import model.item.IFlat
import ui.screen.itemsList.baseItemAddItem
import ui.screen.itemsList.baseItemBody
import ui.utils.Resources

private val viewModel: FlatVM = FlatVM()

@Composable
fun flatBody(
    flat: IFlat,
    index: Int,
) {

    baseItemBody(
        icon = Resources.getIcon("items/horizontal_rule24"),
        onNameChange = { viewModel.setName(it, index) },
        onEditClick = { viewModel.remove(index) },
        item = flat
    ) {

        baseFlat(
            value = flat.value.value,
            onLess = { viewModel.onLess(index, flat.value.value) },
            onMore = { viewModel.onMore(index, flat.value.value) },
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