package ui.screen.body.tempList


import Source
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import model.item.LibItem
import ui.component.baseSensor
import ui.utils.Resources


private val viewModel: TempViewModel = TempViewModel()

fun LazyListScope.tempList(
    editModeActivated: Boolean
) {
    itemsIndexed(viewModel.tempItemList.value) { index, temp ->
        temp(
            item = temp,
            index = index,
            editModeActivated = editModeActivated
        )
    }
}


@Composable
fun temp(
    item: LibItem,
    index: Int,
    editModeActivated: Boolean
) {
    val viewModel = TempViewModel()

    baseSensor(
        iconPainter = Resources.getIcon("thermometer"),
        iconContentDescription = "",
        name = item.name,
        label = "name",
        onNameChange = {
            viewModel.setName(it, index)
        },
        editModeActivated = editModeActivated,
        onEditClick = {
            viewModel.remove(index)
        },
        source = Source.BODY,
        sensorName = item.sensorName,
        onChangeSensorClick = {

        },
        sensorValue = viewModel.tempList.value.filter {
            it.libId == item.sensorId
        }[0].value.toString() + " °C"
    )
}


