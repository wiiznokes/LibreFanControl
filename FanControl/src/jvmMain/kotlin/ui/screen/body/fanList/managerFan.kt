package ui.screen.body.fanList

import Source
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import model.item.LibItem
import ui.component.baseSensor
import ui.utils.Resources


private val viewModel: FanViewModel = FanViewModel()


fun LazyListScope.fanList(
    editModeActivated: Boolean
) {
    itemsIndexed(viewModel.fanItemList.value) { index, item ->
        fan(
            item = item,
            index = index,
            editModeActivated = editModeActivated
        )
    }
}


@Composable
fun fan(
    item: LibItem,
    index: Int,
    editModeActivated: Boolean
) {
    val viewModel = FanViewModel()

    baseSensor(
        iconPainter = Resources.getIcon("toys_fan"),
        iconContentDescription = "",
        name = item.name,
        label = "name",
        onNameChange = {
            viewModel.setName(it, index)
        },
        editModeActivated = editModeActivated,
        onEditClick = {
            viewModel.remove(index)
        },
        sensorName = item.sensorName,
        onChangeSensorClick = {

        },
        source = Source.BODY,
        sensorValue = viewModel.fanList.value.filter {
            it.libId == item.sensorId
        }[0].value.toString() + " RPM"
    )
}

