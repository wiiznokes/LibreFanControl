package ui.screen.body.tempList

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import model.item.SensorItem
import ui.component.baseSensorBody
import ui.utils.Resources


private val viewModel: TempViewModel = TempViewModel()

fun LazyListScope.tempList(
    editModeActivated: Boolean
) {
    itemsIndexed(viewModel.tempItemList.value) { index, temp ->
        temp(
            sensorItem = temp,
            index = index,
            editModeActivated = editModeActivated
        )
    }
}


@Composable
fun temp(
    sensorItem: SensorItem,
    index: Int,
    editModeActivated: Boolean
) {

    val sensorValue = if (sensorItem.sensorId != null) {
        viewModel.tempList.value.filter {
            it.libId == sensorItem.sensorId
        }[0].value
    } else 0

    baseSensorBody(
        iconPainter = Resources.getIcon("thermometer"),
        iconContentDescription = Resources.getString("temp_icon_content_description"),
        name = sensorItem.name,
        onNameChange = {
            viewModel.setName(it, index)
        },
        editModeActivated = editModeActivated,
        onEditClick = {
            viewModel.remove(index)
        },
        sensorName = sensorItem.sensorName,
        sensorValue = "$sensorValue  ${Resources.getString("degree")}",
        sensorList = viewModel.tempList.value,
        onItemClick = {
            viewModel.setTemp(index, it)
        }
    )
}


