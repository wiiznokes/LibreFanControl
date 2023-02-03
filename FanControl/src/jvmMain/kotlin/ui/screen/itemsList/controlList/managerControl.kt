package ui.screen.itemsList.controlList


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import model.item.control.ControlItem
import ui.component.managerListChoice
import ui.component.managerText
import ui.screen.itemsList.baseItemAddItem
import ui.screen.itemsList.baseItemBody
import ui.utils.Resources


private val viewModel: ControlVM = ControlVM()

fun LazyListScope.controlAddItemList() {
    item { controlAddItem() }
}

fun LazyListScope.controlBodyList(){

    itemsIndexed(viewModel.iControls) { index, control ->
        controlBody(
            controlItem = control,
            index = index
        )
    }

}


@Composable
fun controlBody(
    controlItem: ControlItem,
    index: Int
) {
    val control = if (controlItem.controlId != null) {
        viewModel.hControls.find {
            it.id == controlItem.id
        }
    } else null

    baseItemBody(
        icon = Resources.getIcon("items/alternate_email40"),
        onNameChange = { viewModel.setName(it, index) },
        onEditClick = { viewModel.remove(index) },
        item = controlItem
    ) {
        Row {

            Switch(
                modifier = Modifier
                    .scale(0.8f)
                    .wrapContentSize(),
                checked = !controlItem.isAuto,
                onCheckedChange = {
                    viewModel.onSwitchClick(it, index)
                }
            )
            Spacer(Modifier.width(10.dp))

            managerListChoice(
                text = viewModel.iBehaviors.firstOrNull {
                    it.id == controlItem.behaviorId
                }?.name,
                onItemClick = { viewModel.setBehavior(index, it) },
                ids = viewModel.iBehaviors.map { it.id },
                names = viewModel.iBehaviors.map { it.name }
            )
        }
        managerText(
            text = "${control?.value ?: 0} ${Resources.getString("unity/percent")}",
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}




@Composable
fun controlAddItem() {
    baseItemAddItem(
        icon = Resources.getIcon("items/alternate_email40"),
        name = "Control",
        onEditClick = { viewModel.addControl() }
    ) {

    }
}