package ui.screen.body.fanList

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import kotlinx.coroutines.flow.asFlow
import model.item.SensorItem
import ui.component.baseSensorBody
import ui.utils.Resources


private val viewModel: FanViewModel = FanViewModel()


fun LazyListScope.fanList() {
    itemsIndexed(viewModel.fanItemList) { index, item ->
        fan(
            sensorItem = item,
            index = index
        )
    }
}


@Composable
fun fan(
    sensorItem: SensorItem,
    index: Int
) {
    val sensor = if (sensorItem.sensorId != null) {
        viewModel.fanList.find {
            it.id == sensorItem.sensorId
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

