package ui.screen.itemsList.sensor.body.fanList

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import model.item.IFan
import ui.screen.itemsList.sensor.baseSensorBody
import ui.utils.Resources


private val viewModel: FanVM = FanVM()


fun LazyListScope.fanBodyList() {
    itemsIndexed(viewModel.iFans) { index, iFan ->
        fanBody(
            iFan = iFan,
            index = index
        )
    }
}


@Composable
private fun fanBody(
    iFan: IFan,
    index: Int,
) {
    val sensor = if (iFan.hFanId.value != null) {
        viewModel.hFans.find {
            it.id == iFan.hFanId.value
        }
    } else null

    baseSensorBody(
        icon = Resources.getIcon("items/toys_fan24"),
        onNameChange = { viewModel.setName(it, index) },
        onEditClick = { viewModel.remove(index) },
        sensorName = sensor?.name,
        sensorValue = "${sensor?.value?.value ?: 0} ${Resources.getString("unity/rpm")}",
        sensorList = viewModel.hFans,
        onItemClick = { viewModel.setFan(index, it) },
        iSensor = iFan
    )
}

