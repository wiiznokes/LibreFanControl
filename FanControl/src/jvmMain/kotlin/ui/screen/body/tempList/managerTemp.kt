package ui.screen.body.tempList


import Source
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import model.item.SensorItem
import ui.component.baseSensor
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

    val sensor = viewModel.tempList.value.filter {
        it.libId == sensorItem.sensorId
    }[0]

    baseSensor(
        iconPainter = Resources.getIcon("thermometer"),
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
        source = Source.BODY,
        sensorName = sensorItem.sensorName,
        onChangeSensorClick = {

        },
        sensorValue = "${sensor.value} °C"
    )
}


