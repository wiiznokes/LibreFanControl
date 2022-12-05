package ui.screen.body.tempList


import Source
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import model.hardware.sensor.Temp
import ui.component.baseSensor
import ui.utils.Resources



private val viewModel: TempViewModel = TempViewModel()

fun LazyListScope.tempList (
    editModeActivated: Boolean
) {
    itemsIndexed(viewModel.tempList.value.filter {
        it.visible
    }) { index, temp ->
        temp(
            temp = temp,
            index = index,
            editModeActivated = editModeActivated
        )
    }
}


@Composable
fun temp(
    temp: Temp,
    index: Int,
    editModeActivated: Boolean
) {
    val viewModel = TempViewModel()

    baseSensor(
        iconPainter = Resources.getIcon("thermometer"),
        iconContentDescription = "",
        name = temp.name,
        label = "name",
        onNameChange = {
            viewModel.setName(it, index)
        },
        editModeActivated = editModeActivated,
        onEditClick = {
            viewModel.remove(index)
        },
        source = Source.BODY,
        sensorName = temp.libName,
        onChangeSensorClick = {

        },
        sensorValue = "${temp.value} °C"
    )
}


