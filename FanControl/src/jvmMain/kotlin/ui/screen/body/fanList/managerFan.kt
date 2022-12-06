package ui.screen.body.fanList

import Source
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

    val sensor = viewModel.fanList.value.filter {
        it.libId == sensorItem.sensorId
    }[0]

    baseSensor(
        iconPainter = Resources.getIcon("toys_fan"),
        iconContentDescription = "",
        name = sensorItem.name,
        label = "name",
        onNameChange = {
            viewModel.setName(it, index)
        },
        editModeActivated = editModeActivated,
        onEditClick = {
            viewModel.remove(index)
        },
        sensorName = sensorItem.sensorId,

        source = Source.BODY,
        sensorValue = "${sensor.value} RPM",
        sensorList = viewModel.fanList.value,
        onItemClick = {
            viewModel.setFan(index, it)
        }
    )
}

