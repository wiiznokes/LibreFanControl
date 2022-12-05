package ui.screen.body.fanList

import Source
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.StateFlow
import model.hardware.sensor.Fan
import ui.component.baseSensor
import ui.utils.Resources



val viewModel: FanViewModel = FanViewModel()


fun LazyListScope.fanList (
    editModeActivated: Boolean
) {
    itemsIndexed(viewModel.fanList.value) {index, fan ->
        fan(
            fan = fan,
            index = index,
            editModeActivated = editModeActivated
        )
    }
}





@Composable
fun fan(
    fan: Fan,
    index: Int,
    editModeActivated: Boolean
) {
    val viewModel = FanViewModel()

    baseSensor(
        iconPainter = Resources.getIcon("toys_fan"),
        iconContentDescription = "",
        name = fan.name,
        label = "name",
        onNameChange = {
            viewModel.setName(it, index)
        },
        editModeActivated = editModeActivated,
        onEditClick = {
            viewModel.remove(index)
        },
        source = Source.BODY,
        sensorName = fan.libName,
        onChangeSensorClick = {

        },
        sensorValue = "${fan.value} RPM"
    )
}

