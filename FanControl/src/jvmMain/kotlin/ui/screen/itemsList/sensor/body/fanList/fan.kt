package ui.screen.itemsList.sensor.body.fanList

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import model.item.sensor.Fan
import model.item.sensor.SensorItem
import ui.screen.itemsList.sensor.baseSensorBody
import ui.utils.Resources


private val viewModel: FanVM = FanVM()


fun LazyListScope.fanBodyList() {
    itemsIndexed(viewModel.fanItemList) { index, item ->
        fanBody(
            sensorItem = item,
            index = index
        )
    }
}


@Composable
private fun fanBody(
    sensorItem: SensorItem,
    index: Int
) {
    val sensor = if ((sensorItem.extension as Fan).sensorId != null) {
        viewModel.fanList.find {
            it.id == sensorItem.extension.sensorId
        }
    } else null

    viewModel.fanList

    baseSensorBody(
        iconPainter = Resources.getIcon("toys_fan"),
        iconContentDescription = Resources.getString("ct/fan"),
        onNameChange = { viewModel.setName(it, index) },
        onEditClick = { viewModel.remove(index) },
        sensorName = sensor?.libName,
        sensorValue = "${sensor?.value ?: 0} ${Resources.getString("unity/rpm")}",
        sensorList = viewModel.fanList,
        onItemClick = { viewModel.setFan(index, it) },
        sensorItem = sensorItem
    )
}

