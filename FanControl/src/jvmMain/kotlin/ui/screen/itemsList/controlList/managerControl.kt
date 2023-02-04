package ui.screen.itemsList.controlList


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.item.control.ControlItem
import ui.component.managerListChoice
import ui.component.managerText
import ui.screen.itemsList.baseItemAddItem
import ui.screen.itemsList.baseItemBody
import ui.theme.LocalColors
import ui.theme.LocalSpaces
import ui.utils.Resources


private val viewModel: ControlVM = ControlVM()

fun LazyListScope.controlAddItemList() {
    item { controlAddItem() }
}

fun LazyListScope.controlBodyList() {

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
            it.id == controlItem.controlId
        }!!
    } else null

    val behavior = if (controlItem.behaviorId != null) {
        viewModel.iBehaviors.find {
            it.id == controlItem.behaviorId
        }!!
    } else null


    baseItemBody(
        icon = Resources.getIcon("items/alternate_email24"),
        onNameChange = { viewModel.setName(it, index) },
        onEditClick = { viewModel.remove(index) },
        item = controlItem
    ) {

        managerListChoice(
            text = control?.name,
            onItemClick = { viewModel.setControl(index, it) },
            ids = viewModel.hControls.map { it.id },
            names = viewModel.hControls.map { it.name }
        )

        Spacer(Modifier.height(LocalSpaces.current.medium))

        managerListChoice(
            text = behavior?.name,
            onItemClick = { viewModel.setBehavior(index, it) },
            ids = viewModel.iBehaviors.map { it.id },
            names = viewModel.iBehaviors.map { it.name }
        )

        Spacer(Modifier.height(LocalSpaces.current.medium))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            managerText(
                text = "${control?.value ?: 0} ${Resources.getString("unity/percent")}",
                color = LocalColors.current.onMainContainer
            )

            Switch(
                modifier = Modifier
                    .scale(0.75f)
                    .requiredSize(width = 45.dp, height = 20.dp),
                checked = !controlItem.isAuto,
                onCheckedChange = { viewModel.onSwitchClick(it, index) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = LocalColors.current.onInput,
                    checkedTrackColor = LocalColors.current.input,
                    uncheckedThumbColor = LocalColors.current.onMainSurface,
                    uncheckedTrackColor = LocalColors.current.mainSurface,
                )
            )
        }
    }
}


@Composable
fun controlAddItem() {
    baseItemAddItem(
        icon = Resources.getIcon("items/alternate_email24"),
        name = "Control",
        onEditClick = { viewModel.addControl() }
    ) {

    }
}