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


    val sensor = if (sensorItem.sensorId != null) {
        viewModel.tempList.value.find {
            it.id == sensorItem.itemId
        }
    } else null

    baseSensorBody(
        iconPainter = Resources.getIcon("thermometer"),
        iconContentDescription = Resources.getString("ct/temp"),
        onNameChange = { viewModel.setName(it, index) },
        editModeActivated = editModeActivated,
        onEditClick = { viewModel.remove(index) },
        sensorName = sensor?.libName,
        sensorValue = "${sensor?.value ?: 0} ${Resources.getString("unity/degree")}",
        sensorList = viewModel.tempList.value,
        onItemClick = { viewModel.setTemp(index, it) },
        sensorItem = sensorItem
    )
}


