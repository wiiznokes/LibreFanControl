package ui.screen.addItem.control

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import model.item.ControlItem
import ui.component.baseControlAddItem
import ui.utils.Resources
import utils.filterWithPreviousIndexComposable


val viewModel = AddControlViewModel()


fun LazyListScope.managerAddControl() {
    filterWithPreviousIndexComposable(
        list = viewModel.controlItemList,
        predicate = { !it.visible }
    ) { index, control ->
        controlAddItem(
            control = control,
            index = index
        )
    }
}


@Composable
private fun controlAddItem(
    control: ControlItem,
    index: Int
) {


    baseControlAddItem(
        name = control.name,
        onEditClick = {
            viewModel.addControl(index)
        },
        behaviorName = Resources.getString("add_item/choose_behavior"),
        value = "${control.value} ${Resources.getString("unity/percent")}",
        fanValue = ""
    )
}