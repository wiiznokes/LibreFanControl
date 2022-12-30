package ui.screen.body.fanList

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import model.item.SensorItem
import ui.component.baseSensorBody
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
    val sensor = if (sensorItem.sensorId != null) {
        viewModel.fanList.value.find {
            it.id == sensorItem.sensorId
        }
    } else null


    baseSensorBody(
        iconPainter = Resources.getIcon("toys_fan"),
        iconContentDescription = Resources.getString("ct/fan"),
        onNameChange = { viewModel.setName(it, index) },
        editModeActivated = editModeActivated,
        onEditClick = { viewModel.remove(index) },
        sensorName = sensor?.libName,
        sensorValue = "${sensor?.value ?: 0} ${Resources.getString("unity/rpm")}",
        sensorList = viewModel.fanList.value,
        onItemClick = { viewModel.setFan(index, it) },
        sensorItem = sensorItem
    )
}

