package ui.screen.body.controlList


import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import model.item.ControlItem
import ui.component.baseControlBody
import ui.utils.Resources
import utils.filterWithPreviousIndexComposable


private val viewModel: ControlViewModel = ControlViewModel()


fun LazyListScope.controlList() {

    filterWithPreviousIndexComposable(
        list = viewModel.controlItemList,
        predicate = { it.visible }
    ) { index, control ->
        control(
            control = control,
            index = index
        )
    }
}


@Composable
fun control(
    control: ControlItem,
    index: Int
) {
    baseControlBody(
        onNameChange = { viewModel.setName(it, index) },
        onEditClick = {
            viewModel.remove(
                index = index
            )
        },

        onSwitchClick = { checked ->
            viewModel.onSwitchClick(
                checked = checked,
                index = index
            )
        },
        value = "${control.value} ${Resources.getString("unity/percent")}",
        fanValue = "",
        behaviorItemList = viewModel.behaviorItemList,
        onBehaviorChange = {
            viewModel.setBehavior(index, it)
        },
        control = control,
        enabled = !viewModel.controlsChange.collectAsState().value
    )
}



