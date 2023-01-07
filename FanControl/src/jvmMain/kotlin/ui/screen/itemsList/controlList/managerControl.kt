package ui.screen.itemsList.controlList


import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import model.ItemType
import model.item.control.Control
import ui.component.managerAddItemListChoice
import ui.component.managerListChoice
import ui.screen.itemsList.baseItemAddItem
import ui.screen.itemsList.baseItemBody
import ui.utils.Resources
import utils.filterWithPreviousIndexComposable


private val viewModel: ControlVM = ControlVM()


fun LazyListScope.controlList(
    predicate: (Control) -> Boolean,
    content: @Composable (Int, Control) -> Unit
) {
    filterWithPreviousIndexComposable(
        list = viewModel.controlList,
        predicate = predicate
    ) { index, control ->
        content(index, control)
    }
}


@Composable
fun controlBody(
    control: Control,
    index: Int
) {
    baseItemBody(
        iconPainter = Resources.getIcon("items/alternate_email"),
        iconContentDescription = Resources.getString("ct/control"),
        onNameChange = { viewModel.setName(it, index) },
        onEditClick = { viewModel.remove(index) },
        item = control
    ) {
        baseControl(
            isAuto = control.isAuto,
            switchEnabled = !viewModel.controlsChange.collectAsState().value,
            onSwitchClick = { checked ->
                viewModel.onSwitchClick(checked, index)
            },
            value = control.value,
            color = MaterialTheme.colorScheme.onSurface
        ) {
            managerListChoice(
                text = viewModel.behaviorList.firstOrNull {
                    it.id == control.behaviorId
                }?.name,
                onItemClick = { viewModel.setBehavior(index, it) },
                ids = viewModel.behaviorList.map { it.id },
                names = viewModel.behaviorList.map { it.name },
                enabled = !viewModel.controlsChange.collectAsState().value
            )
        }
    }
}


@Composable
fun controlAddItem(
    control: Control,
    index: Int
) {
    baseItemAddItem(
        iconPainter = Resources.getIcon("items/alternate_email"),
        iconContentDescription = Resources.getString("ct/control"),
        name = control.name,
        onEditClick = { viewModel.addControl(index) },
        type = ItemType.ControlType.I_C_FAN
    ) {
        baseControl(
            isAuto = true,
            switchEnabled = false,
            onSwitchClick = {},
            value = control.value,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        ) {
            managerAddItemListChoice(
                name = Resources.getString("add_item/choose_behavior")
            )
        }
    }
}