package ui.screen.body.fanList

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import model.item.SensorItem
import ui.component.baseSensor
import ui.utils.Resources


private val viewModel: FanViewModel = FanViewModel()


fun LazyListScope.fanList(
    editModeActivated: Boolean,

    ) {
    itemsIndexed(viewModel.fanItemList.value) { index, item ->
        fan(
            sensorItem = item,
            index = index,
            editModeActivated = editModeActivated
        )
    }
}


@Composable
fun fan(
    sensorItem: SensorItem,
    index: Int,
    editModeActivated: Boolean
) {
    val sensorValue = if (sensorItem.sensorId != null) {
        viewModel.fanList.value.filter {
            it.libId == sensorItem.sensorId
        }[0].value
    } else 0


    baseSensor(
        iconPainter = Resources.getIcon("toys_fan"),
        iconContentDescription = Resources.getString("fan_icon_content_description"),
        name = sensorItem.name,
        onNameChange = {
            viewModel.setName(it, index)
        },
        editModeActivated = editModeActivated,
        onEditClick = {
            viewModel.remove(index)
        },
        sensorName = sensorItem.sensorName,

        sensorValue = "$sensorValue ${Resources.getString("rpm")}",
        sensorList = viewModel.fanList.value,
        onItemClick = {
            viewModel.setFan(index, it)
        }
    )
}

